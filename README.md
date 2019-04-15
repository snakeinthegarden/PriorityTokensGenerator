# PriorityTokensGenerator
This project is an acaedmic project in partnership with an Industry Partner.
The goal of the project is to determine if meaning can be drawn from text-based email content.

## Project Overview
The program reads in emails from a Corpus, filters out email metadata and stopwords and outputs tokenized files to output/directory.
The program reads in a single email, filters out email metadata and stopwords and outputs tokenized files to output/single.

The program calculates the priority tokens for the single email by comparing it to the corpus of emails using 2 methods:
 - Method A: Term Frequency-Inverse Document Frequency (TF-IDF)
 - Method B: Dictionary
Both methods use the same stop words list for removing stop words, and the same method of removing email metadata.
The program reads in the tokenized emails from the output folders and calculates priority tokens for both Method A and Method B, and then it outputs the tokens to output/priority.

The main method for the application is in the package `nuig.prioritytokens` in class `App.java`.
There are JUnit tests for methods of 6 core service classes under src/test/java.
There is 1 exceptions class for the application to catch when no corpus is loaded.


## Setup Notes:
1. Extract the zip file to your Eclipse workspace (or any other folder where you keep your projects).
2. Delete the README files within the following folders:
      * output
      * output/directory
      * output/priority
      * output/single
      * output/Tests
3. Run 'mvn install' to download dependancies using Maven. Dependencies include JUnit and OpenNLP.
4. Run the application App.java prior to running JUnit tests.
5. Run the application App.java by passing the arguement "loadCorpus" to the main method
      * Failure to upload the corpus prior to attempting to analyse the tokens will result in an error thrown to the user in the console.
      * For each subsequent run of the application (i.e if the user wishes to pass alternative singleEmail files), no arguement is required to be passed to the main method. In this case, the Application will read in an already tokenized corpus. However, if it is preferred to re-generate a newly tokenized corpus on each run, then pass the arguement "loadCorpus" each time the App.java runs.
      * Enter the arguement by right-clicking on App.java, click Run As -> Run Configurations - > Arguements. Then type the string "loadCorpus" into the field "Program Arguements". Then Click Run.
6. Complete the following JUnit tests by right-clicking on each .java file, click Run As -> JUnit Test
      * MethodA_TFIDFTest.java
      * MethodB_DictionaryTest.java
      * EmailReaderServiceTest.java
      * MyProjectServiceTest.java
      * TokenizerServiceTest.java
      * UtilServiceTest.java


## Constraints of the Project
* The industry partner required the use of OpenNLP as the tokenizing technology.
    - This library requires the use of OpenNLP models (input/BinFiles) and the OpenNLP library added using Maven dependencies
* The industry partner preferred use of the Enron Corpus as the basis of the data, and to use approximately 5000 emails.
* The academic institution required a directory of emails that was small enough to run quickly during presentation, so a corpus of 312 emails was used in the project. (but 5 corpus sizes are available at corpusDirectories folder in the project)
* The industry partner required java 1.8 as the basis for the program.
* The industry partner preferred output to flat file of tokenized information prior to analysis of tokens.
* The industry partner required that the TF-IDF Method was used to determine the priority tokens for email content.
* The academic institution required additional independant research, so an alternative method was generated to determine priority tokens for email content.
* Empty output folders are required for the application to tokenize and/or analyse the tokens correctly. Temporary README files have been places in the output folder. These must be deleted prior to running the program.
* The academic institution required testing be completed using JUnit framework, and the library for this was added using Maven dependencies.
* The industry partner and the student use IDE Eclipse, so testing and design for other IDE systems was not completed.
