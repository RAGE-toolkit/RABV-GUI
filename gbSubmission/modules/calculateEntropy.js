function generateBatchSqnFiles(source, metadata, template) {

  import source source
  
  in mode
  module rabvSubmissionMetadataTextFilePopulator populate -w "source.name = "+source, -f metadata

  # Do genotyping
  #module rabvMaxLikelihoodGenotyper genotype sequence -w "source.name = 'batch-2022'" source
   module rabvMaxLikelihoodGenotyper genotype sequence -w "source.name = 'batch-2023'" source

  //run script glue/rabvSubmissionAddSequencesToAlmtTree.js
  
  #!!!!!
  
  
  # Record feature presence
  run file glue/recordFeaturePresence.glue
  module rabvFeaturePresenceRecorder 

    #record feature-presence AL_MASTER --recursive --whereClause "sequence.source.name = 'batch-2021'" --featureName whole_genome --descendentFeatures
    #record feature-presence AL_MASTER --recursive --whereClause "sequence.source.name = 'batch-2022'" --featureName whole_genome --descendentFeatures
    record feature-presence AL_MASTER --recursive --whereClause "sequence.source.name = 'batch-2023'" --featureName whole_genome --descendentFeatures

    exit



  # Generate the submission files 
  #module rabvSubmissionGenerator generate-sqn -w "source.name = " source -t template -o gb/batch-2021
  module rabvSubmissionGenerator2023 generate-sqn -w "source.name = " source -t template -o gb/batch-2023

}


function addSequencesToAlmtTree(source) {

	var genotypingResultObjs;

	glue.inMode("module/rabvMaxLikelihoodGenotyper", function() {
		genotypingResultObjs = glue.tableToObjects(glue.command(["genotype", "placer-result", "-f", "placements/batch-2023_placements.xml"]));
		glue.logInfo("genotypingResultObjs : "+genotypingResultObjs);

	});

	_.each(genotypingResultObjs, function(genotypingResultObj) {

		var queryBits = genotypingResultObj.queryName.split("/");
		var sourceName = queryBits[0];
		var sequenceID = queryBits[1];
		var targetAlignmentName;
	
		if(genotypingResultObj.minor_cladeFinalClade != null) {
			targetAlignmentName = genotypingResultObj.minor_cladeFinalClade;
			var majorCladeAlmtName = genotypingResultObj.major_cladeFinalClade;
			glue.inMode("sequence/"+sourceName+"/"+sequenceID, function() {
				glue.command(["set", "field", "major_clade", majorCladeAlmtName.replace("AL_", "")]);
				glue.command(["set", "field", "minor_clade", targetAlignmentName.replace(majorCladeAlmtName+"_", "")]);
			});
		} else if(genotypingResultObj.minor_cladeFinalClade != null) {
			targetAlignmentName = genotypingResultObj.major_cladeFinalClade;
			glue.inMode("sequence/"+sourceName+"/"+sequenceID, function() {
				glue.command(["set", "field", "major_clade", targetAlignmentName.replace("AL_", "")]);
			});
		} else {
			targetAlignmentName = "AL_MASTER";
		}
	
		glue.inMode("alignment/"+targetAlignmentName, function() {
			glue.command(["add", "member", sourceName, sequenceID]);
		});
	
		glue.command(["compute", "alignment", targetAlignmentName, "rabvCompoundAligner",
					  "--whereClause", "sequence.source.name = '"+sourceName+"' and sequence.sequenceID = '"+sequenceID+"'"]);
	
	});
	
}