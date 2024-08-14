GLUE-GUI is a graphical user interface designed for running RABV-GLUE, specifically to facilitate GenBank submissions for rabies sequences. In addition to supporting GenBank submissions, GLUE-GUI has been updated with the latest rabies sequences and can perform database queries similar to those available on the RABV-GLUE website (http://rabv-glue.cvr.gla.ac.uk/).

#Installation
GLUE_GUI is developed in Java, so having Java installed is essential. We recommend using Conda to run GLUE-GUI, which makes the execution process simple.

## Installing Conda 
MAC user can download Conda from below source, avoid this step if the MiniForge or Miniconda is already installed.

```shell
https://github.com/conda-forge/miniforge
```

Linux user can follow this link for Conda download.
```shell
https://docs.anaconda.com/miniconda/
```
Note: Download and install the appropriate version of the Conda as the your OS.


## Download and setup the environments

Download the GLUE-GUI from HTTPS:/\

```shell
$unzip glue-gui_v1.0.0.gz
$cd glue-gui_v1.0.0
$conda env create --file environment.yml
$conda env create --file gluetools.yml
```

##Installing the MySQL
Select the appropriate version of the MySQL Community Server according to your operating system. The gluetools recommends MySQL Community Server 8.0.39 which can be found in the below link.

```shell
https://dev.mysql.com/downloads/mysql/
```
Setup the mySQL with appropriate root user and credentials for the error free post setups.

### Post SQL installations settings
The gluetools requires its own mysql user to communicate with glue database. The post setup is as follows. Which can be accomplished in five easy steps

Step 1: login to mysql with root user
```shell
$mysql -h localhost -u root -p
```
Step 2: create gluetools user with password
```shell
$mysql> create user 'gluetools'@'localhost' identified by 'Password123#@!';
```
Step3: create a database GLUE_TOOLS
```shell
$mysql> create database GLUE_TOOLS character set UTF8;
```
Step4: grant all the privileges to database (GLUE_TOOLS)
```shell
$mysql> grant all privileges on GLUE_TOOLS.* to 'gluetools'@'localhost';
```
Step5: import the SQL database to GLUE_TOOLS database
```shell
$mysql> SOURCE glue_jar_files/ncbi_rabv_glue.sql
```

## Setup the gluetools config file
Gluetools uses its own config while which is a XML file contains the path of the various tools used by the gluetools. 

To edit the path you can open the file from a directory "gluetoos_jar_file" a file named gluetools-config.xml. All the required software for the gluetools is installed by the gluetools.yml environment you can activate it by running below command

Gluetools uses a custom configuration file, which is an XML file that specifies the paths to various tools utilized by Gluetools. To edit these paths, open the "gluetools-config.xml" file located in the "gluetools_jar_file" directory. All the necessary software for Gluetools is installed through the "gluetools.yml" environment, which can be activated by running the following command:

### get software path
```shell
conda activate gluetools
```
```shell
which blasts
```
You may have similar path /Users/user-name/miniforge3/envs/gluetools/bin/blastn
```shell
which tblastn
```
You may have similar path /Users/user-name/miniforge3/envs/gluetools/bin/tblastn
```shell
which makeblastdb
```
You may have similar path /Users/user-name/miniforge3/envs/gluetools/bin/makeblastdb
```shell
which table2asn 
```
You may have similar path /Users/user-name/miniforge3/envs/gluetools-testing/bin/table2asn
```shell
which mafft
```
You may have similar path /Users/user-name/miniforge3/envs/gluetools-testing/bin/mafft

### Set temorary file paths for above listed software

## Running the GUI
Once all the above setups are successful, it is good to test if the glue-gui it working correctly or not. glue-gui can be run with using the script glue-gui.sh. This file contains the parameters necessary to run the GUI. The GUI requires javafx-sdk to run the gui, the javafx-sdk by default comes with the glue-gui tool. Upon any errors. You can run download the appropriate sdk from the https://openjfx.io

### Lanuching the GUI
```shell
bash glue-gui.sh
```

The successful installation will launch the GLUE-GUI. 

### Initial setups for GUI

The first time GUI launch will require few things to be setup such as path of the gluetools jar file and its configuration files.

#### Setting up the glue and path
goto-->settings-->Set glue and DB --> browse the gluetools jar and configuration files from gluetoos_jar_file directory with the GLUE-GUI

## Running the analysis
At this moment, Glue-GUI provides two functions. 1) maxLikeHoodGenotyper and 2) genBankSubmission. 

maxLikeHoodGenotyper requires rabies fasta file to tell which alignment the sequence belongs. 

genBankSubmission function, generates the GenBank sequence submission files (*.SQN). It uses the latest table2asn tool to generate the SQN files. It requires fasta file directory, submission template and tabular file as an input to generate the SQN files.





