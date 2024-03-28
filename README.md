<div align="center">
<img src = src/icon.png>
<h2>
 ANDIE - A Non-Destructive Image Editor

Made by the CodeCrafters Team
</h2>


</div>

## Contribution
`Eden`
* Image export
* Improved Save function
* Improved Save as function
* Improved File Opening function
* Improved File Exit function
* Colour Channel Cycling
* Error Handling (yes with funny messages)
* Adding Traditional Chinese (Taiwan) language support
* Created the icon

`Kevin`
* Enabled Multilingual support 
* Image flip (Horizontal and Vertical)
* Median filter
* Image rotation - polishing
* Image invert (Worked with Yuxing)
* Added Bahasa Indonesia as language option
* Added a Help option and an About Us.
* Testing and debugging

`Yuxing`
* Image resize
* Image invert
* Image scaling

`Emma`
* Sharpen filter
* Soft blur filter
* Black image border issue fix
* Creating Readme.md
* Testing whole project during runtime for bugs
* Created Issues.txt

`Angus`
* Guassian blur filter
* Image rotation
* Testing
* User instuctions read me


## Testing
We tested our code mainly via brute force. We went through and thought of all the senarios that could occur when a user would use the program and serched for errors. We also checked for silent errors by using try catch loops and having the program print out a message to the terminal anytime an error was caught.

## Known Issues
- When user is prompted to enter a value for an action, if they exit out of the pop-up, the action will still be performed with the default value.
- When the median filter is applied, the outer ring of pixels is effected adversely. As it does not use a convolution, the same method we applied to the other filters to fix this doesn't work.
- For the remaining filters, when applied, the image will lose its outer ring of pixels each time they are applied
- If an image's ops file is deleted after actions are performed, when the user exits they will not be prompted to save
- Rotating images by arbitrary angles is an experimental feature and is needing refinement
- UI is not as harmonious as we would like it to be

## User Guide

Prerequisites
- Java Development Kit (JDK): You'll need a recent version of the JDK installed on your system. Download and install it from the official Oracle website: https://www.oracle.com/nz/java/technologies/downloads/. Verify the installation by opening a terminal and running java -version.

- Gradle: Version 8.6

Installation
- Clone or Download the Project: Obtain the project files using your preferred method (Git clone or direct download).

Running the program
- Open Terminal and navigate to project directory
- Run
`gradle build` 
- Run `gradle run`
- to open files go to file > open

## Significant Refactoring

None done yet. 

## Gantt Diagram for Our Project

```mermaid

%% CodeCrafters' Gantt Chart for our work on ANDIE
%%{init: {'theme':'forest'}}%%
        gantt
        title CodeCrafters' work on ANDIE
        dateFormat  DD-MM-YYYY
        Start of project: milestone, m1,05-03-2024, 
        First deliverable: milestone, m2, 28-03-2024,

        section Developing
            Colour Channel Cycling              :done, des1, 08-03-2024, 3d
            Image Opening                       :done,  des2, 14-03-2024, 19-03-2024
            File Saving                         :done,  des2, 14-03-2024, 19-03-2024
            File Save As                        :done,  des2, 14-03-2024, 19-03-2024
            File Export                         :done,  des2, 17-03-2024, 19-03-2024
            Multilingual support                :active, des2, 14-03-2024, 27-03-2024
            Help Action                         :done, des1, 06-03-2024, 07-03-2024
            Image flip                          :done, des1, 16-03-2024, 17-03-2024
            Median filter                       :done, des2, 13-03-2024, 15-03-2024
            Gaussian Filter               :done, 08-03-2024, 09-03-2024
            Image Rotation                :done, 16-03-2024, 17-03-2024
            Sharpen Filter                 :done, des2, 10-03-2024, 11-03-2024
            Soft Blur Filter               :done, des2, 11-03-2024, 12-03-2024
            README                         :done, des2, 26-03-2024, 28-03-2024



        section Debugging
            Image Opening                      :done , 22-03-2024, 23-03-2024
            Image Opening                      :done , 27-03-2024, 28-03-2024
            General Testing                    :done, 27-03-2024, 28-03-2024
            File Export                         :done, 27-03-2024, 28-03-2024
            File Saving                         :done, 27-03-2024, 28-03-2024
            File Save As                        :done, 22-03-2024, 27-03-2024
            Multilingual support                :done, 26-03-2024, 27-03-2024
            Handling Merge Conflict             :done, 10-03-2024, 11-03-2024
            Handling Merge Conflict             :done, 12-03-2024, 13-03-2024
            Handling Merge Conflict             :done, 15-03-2024, 16-03-2024
            Handling Merge Conflict             :done, 26-03-2024, 27-03-2024
            Filter Border Fix                   :done, 13-03-2024, 18-03-2024



```