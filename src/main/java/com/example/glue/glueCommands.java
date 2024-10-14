package com.example.glue;

import java.util.ArrayList;
import java.util.List;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.io.FileWriter;
import java.io.IOException;


public class glueCommands {

    public static String getBaseDirName(String filePath) {
        Path path = Paths.get(filePath);
        return path.getFileName().toString();
    }

    public static List<String>  maxLikelihoodGenotyper(String projectName, String fastaFile) {
        List<String> maxLikelihoodGenotyper = new ArrayList<>();
        maxLikelihoodGenotyper.add("project " + projectName);
        maxLikelihoodGenotyper.add("module rabvMaxLikelihoodGenotyper genotype file --fileName " + fastaFile);
        return maxLikelihoodGenotyper;
    }

    public static List<String>  maxLikelihoodGenotyperWithOutputExtracted (String projectName, String fastaFile, String outputDir) {
        List<String> maxLikelihoodGenotyperWithOutputExtracted = new ArrayList<>();
        maxLikelihoodGenotyperWithOutputExtracted.add("project " + projectName);
        maxLikelihoodGenotyperWithOutputExtracted.add("module rabvMaxLikelihoodGenotyper genotype file --fileName " + fastaFile + " --documentResult --dataDir " + outputDir);
        return maxLikelihoodGenotyperWithOutputExtracted;
    }

    public static List<String>  sequenceReporter (String projectName, String fastaFile, String outputDir) {
        List<String> sequenceReporter = new ArrayList<>();
        sequenceReporter.add("project " + projectName);
        sequenceReporter.add("module rabvSequenceReporter amino-acid -i " + fastaFile + " -r REF_MASTER_NC_001542 -f P -t REF_Cosmopolitan_AF1b_KX148208");
        return sequenceReporter;
    }

    public static void writeTextFilePopulatorAndMaxLikelyhood(String filePath, String fileName, String baseDir, String tabularFile, String fastaDir) {
        String importSource = "import source \"" + fastaDir + "\"";
        String textFilePopulator = "module rabvSubmissionMetadataTextFilePopulator populate -w \"source.name = '" + baseDir + "'\" -f " + tabularFile;
        String maxLikelihood = "module rabvMaxLikelihoodGenotyper genotype sequence -w \"source.name = '" + baseDir + "'\"";
        try {
            FileWriter writer = new FileWriter(filePath + "/" + fileName);  // Replace with your desired output file name
            writer.write(importSource + System.lineSeparator());
            writer.write(textFilePopulator + System.lineSeparator());
            writer.write(maxLikelihood + System.lineSeparator());

            writer.close();
            System.out.println("Successfully wrote to the file.");
            }
        catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }

    public static void writeFeatureRecordPresence(String filePath, String fileName, String baseDir) {
        String loadModule = "module rabvFeaturePresenceRecorder";
        String featurePresence = "record feature-presence AL_MASTER --recursive --whereClause \"sequence.source.name = '" + baseDir + "'\" --featureName whole_genome --descendentFeatures";
        String exitModule = "exit";
        try {
            FileWriter writer = new FileWriter(filePath + "/" + fileName);  // Replace with your desired output file name
            writer.write(loadModule + System.lineSeparator());
            writer.write(featurePresence + System.lineSeparator());
            writer.write(exitModule + System.lineSeparator());
            writer.close();
            System.out.println("Successfully wrote to the file:" + filePath + "/" + fileName );
            }
        catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }

    public static void writeTable2asn(String filePath, String fileName, String baseDir, String templateFile, String outputDir) {
        String table2asn = "module rabvSubmissionGenerator generate-sqn -w \"source.name = '" + baseDir + "'\" -t " + templateFile + " -o " + outputDir;
        String validate = "validate";
        String exit = "exit";
        try {
            FileWriter writer = new FileWriter(filePath + "/" + fileName);  // Replace with your desired output file name
            writer.write(table2asn + System.lineSeparator());
            writer.close();
            System.out.println("Successfully wrote to the file:" + filePath + "/" + fileName );
            }
        catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }

    public static List<String>  gbSubmission (String projectName, String fastaDir, String tabularFile, String templateFile, String outputDir) {
        String pwd = HelloController.globalVariables.pwd;
        String baseDir = getBaseDirName(fastaDir);
        List<String> gbSubmission = new ArrayList<>();

        writeTextFilePopulatorAndMaxLikelyhood("gbSubmission/glue", "textPopulatorAndLikelihoodGenotyper.glue", baseDir, tabularFile, fastaDir);
        writeFeatureRecordPresence("gbSubmission/glue", "recordFeaturePresence.glue", baseDir);
        writeTable2asn("gbSubmission/glue", "table2ans.glue", baseDir, templateFile, outputDir);

        gbSubmission.add("schema-project " + projectName);
        gbSubmission.add("run file " + pwd + "/gbSubmission/glue/deleteSubmissionSchemaExtensions.glue");
        gbSubmission.add("run file " + pwd + "/gbSubmission/glue/submissionSchemaExtensions.glue");
        gbSubmission.add("exit");

        gbSubmission.add("project " + projectName);
        gbSubmission.add("delete module --whereClause \"name like '%Submission%'\"");
        gbSubmission.add("delete module rabvMafftAligner");
        gbSubmission.add("delete source " + baseDir);

        gbSubmission.add("run file " + pwd + "/gbSubmission/glue/rabvSubmissionModules.glue");

        //gbSubmission.add("import source " + fastaDir);
        //gbSubmission.add("module rabvSubmissionMetadataTextFilePopulator populate -w \"source.name = '" + baseDir + "'\" -f " + tabularFile);
        //gbSubmission.add("module rabvMaxLikelihoodGenotyper genotype sequence -w \"source.name = '" + baseDir + "'\"");
        gbSubmission.add("run file gbSubmission/glue/textPopulatorAndLikelihoodGenotyper.glue");

        gbSubmission.add("module rabvMaxLikelihoodPlacer");
        gbSubmission.add("place sequence -w \"source.name = '" + baseDir + "'\" --outputFile gbSubmission/placements/placements.xml");
        gbSubmission.add("exit");

        gbSubmission.add("run script " + pwd + "/gbSubmission/glue/rabvSubmissionAddSequencesToAlmtTree.js");

        //gbSubmission.add("module rabvFeaturePresenceRecorder");
        //gbSubmission.add("record feature-presence AL_MASTER --recursive --whereClause \"sequence.source.name = '" + baseDir + "'\" --featureName whole_genome --descendentFeatures");
        //gbSubmission.add("exit");
        gbSubmission.add("run file gbSubmission/glue/recordFeaturePresence.glue");

        //gbSubmission.add("module rabvSubmissionGenerator generate-sqn -w \"source.name = '" + baseDir + "'\" -t " + templateFile + " -o " + outputDir);
        //gbSubmission.add("validate");
        //gbSubmission.add("exit");
        gbSubmission.add("run file gbSubmission/glue/table2ans.glue");

        for (String cmd : gbSubmission) {
            System.out.println("Generated command: " + cmd);
        }

        return gbSubmission;
    }
}
