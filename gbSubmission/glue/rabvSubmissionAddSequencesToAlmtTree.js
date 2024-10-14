var genotypingResultObjs;

glue.inMode("module/rabvMaxLikelihoodGenotyper", function() {
	genotypingResultObjs = glue.tableToObjects(glue.command(["genotype", "placer-result", "-f", "gbSubmission/placements/placements.xml"]));
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
