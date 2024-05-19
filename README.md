<div align="center">
<img src = src/icon.png>
<h2>
 ANDIE - A Non-Destructive Image Editor

Made by the CodeCrafters Team
<p>
ANDIE Code Crafters © 2024 by Emma Boult, Eden Li, Angus Lyall, Kevin Sathyanath, Yuxing Zhang is licensed under CC BY-NC-SA 4.0
</p>
</h2>

[![pipeline status](https://altitude.otago.ac.nz/cosc202-c/andie/badges/main/pipeline.svg)](https://altitude.otago.ac.nz/cosc202-c/andie/-/commits/main)
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
* Implemented multithreading for median filter
* Added two types of keyboard hotkeys and their instructions
* Print Image
* Skeleton Code for ToolBar
* Theme Change

`Kevin`
* Enabled Multilingual support
* Image flip (Horizontal and Vertical)
* Median filter
* Image rotation - polishing
* Image invert (Worked with Yuxing)
* Added Bahasa Indonesia as language option
* Added a Help option and an About Us.
* Testing and debugging
* Added progress bar to median filter
* Random Scattering

`Yuxing`
* Image resize
* Image invert
* Image scaling
* Block averaging
* Drawing functions
* Macros

`Emma`
* Sharpen filter
* Soft blur filter
* Black image border issue fix
* Creating Readme.md
* Testing whole project during runtime for bugs
* Created Issues.txt
* Encapsulation of code to pad border of images - FilterBorder.java
* Extended filters
* Mouse Selection
* Crop to Selection

`Angus`
* Guassian blur filter
* Image rotation
* Testing
* User instuctions read me
* Filters with negative results
* Emboss Filter with edge detection
* Sobel Filter
* Continuous Integration


## Testing
The majority of our testing was done via a brute force method. After each feature was complete we thought of all the scenarios that could occur when a user used said feature and checked for any errors or unexpected outcomes. Once we were completely featured complete, we moved into testing all the different combinations of features available. This allowed us to uncover issues that could occur, particularly with feature that in practice would be commonly used together.
The bulk of our last round of testing was done as an entire group, we booked out a study room for a day and all rigorously tested, trying our best to break our program. This uncovered quite a few errors we had not originally caught, we then noted them all down under the "issues" tab in our Git Lab repository.

Throughout the entirety of our development we had a good system or reporting bugs found in testing. Each time a new bug was found, whoever uncovered it would add it as an "issue" in our Git Lab repository. This way we could all see the complete list of bugs we needed to fix and discuss in the comments on how we planned to fix them. This kept all of our team in the loop and ensured no known bug accidentally got forgotten about.

## Known Issues
- When user is prompted to enter a value for an action, if they exit out of the pop-up, the action will still be performed with the default value.
- If an image's ops file is deleted after actions are performed, when the user exits they will not be prompted to save
- Rotating images by arbitrary angles is an experimental feature and is needing refinement
- UI is not as harmonious as we would like it to be
- When the user opens the dialog to open a different file but then cancels, the selection function will break
- The language support for JColourChooser cannot be implimented. This would be quite tedious and convoulted to fix.

### Previous known issues that have now been fixed
- When the median filter is applied, the outer ring of pixels is effected adversely. As it does not use a convolution, the same method we applied to the other filters to fix this doesn't work. (fixed)
- For the remaining filters, when applied, the image will lose its outer ring of pixels each time they are applied. (fixed)
  
## User Guide

⚠️Prerequisites⚠️
- Java Development Kit (JDK): You'll need a recent version of the JDK installed on your system. Download and install it from the official Oracle website: https://www.oracle.com/nz/java/technologies/downloads/. Verify the installation by opening a terminal and running java -version.

- Gradle: Version 8.6
- FlatLaf: Version 3.2  (https://mavenlibs.com/maven/dependency/com.formdev/flatlaf)

🔧Installation🔧
- Clone or Download the Project: Obtain the project files using your preferred method (Git clone or direct download).

🏃Running the program🏃
- Open Terminal and navigate to project directory
- Run `gradle build`
- Run `gradle run`
- To open files, go to file > open

<span style="color:red;font-weight:700;font-size:16px">
   🔥Hot
</span>keys🔥

<!--https://helpx.adobe.com/tw/photoshop/using/default-keyboard-shortcuts.html-->


| Menus  | <span style="color:red;font-weight:700;font-size:16px">Actions</span>                | Windows                | macOS   | Windows & macOS   | Alternative                    |
|--------|------------------------|------------------------|---------|-------------------|--------------------------------|
| **F**ile   | **O**pen              | Ctrl + O               | ⌘ O     | F - O             |                                |
|        | **S**ave              | Ctrl + S               | ⌘ S     | F - S             |                                |
|        | Save **A**s                | Ctrl + Shift + S       | ⇧⌘ S    | F - A             |                                |
|        | **E**xport                 | Ctrl + E               | ⌘ E     | F - E             |                                |
|        | **P**rint                   | Ctrl + P               | ⌘ P     | F - P             |                                   |
|        | **S**et Language               |                        |         | F - L             |                                |
|        | Exit (**Q**)                   | Ctrl + Q               | ⌘ Q     | F - Q             | Alt + F4 (Windows)             |
| **E**dit   | **U**ndo                   | Ctrl + Z               | ⌘ Z     | E - U             |                                |
|        | **R**edo                   | Ctrl + Y               | ⌘ Y     | E - R             | Ctrl/Cmd + Shift + Y (Win/Mac) |
|        | **C**hange Theme              |                       |         | E - C             |                                 |
| **V**iew   | Zoom **i**n                | Ctrl + "+"             | ⌘ +     | V - I             |                                |
|        | Zoom **o**ut               | Ctrl + "-"             | ⌘ -     | V - O             |                                |
|        | Zoom **f**ull              | Ctrl + 0               | ⌘ 0     | V - F             | Ctrl/Cmd + 1 (Win/Mac)         |
| **I**mage  | Flip **H**orizontal        |                        |         | I - H             |                                |
|        | Flip **V**ertical          |                        |         | I - V             |                                |
|        | Crop                       |                        |         |                   |                                |
|        | **R**otate                 |                        |         | I - R             |                                |
|        | R**e**size                 | Ctrl + Alt + I         | ⌥ ⇧ ⌘ I | I - E             |                                |
|        | R**a**ndom Scattering      |                        |         | I - A             |                                |
|        | **S**caling                |                        |         | I - S - 1/2/3/4/5 |                                |
|        | R**o**tate By              |                        |         | I - O - 1/2/3     |                                |
| Fi**l**ter | **M**ean Filter            |                        |         | L - M             |                                |
|        | **S**oft Blur              |                        |         | L - S             |                                |
|        | S**h**arpen Filter         |                        |         | L - H             |                                |
|        | **G**aussian Filter        |                        |         | L - G             |                                |
|        | Me**d**ian Filter          |                        |         | L - D             |                                |
|        | **E**mboss Filter          |                        |         | L - E             |                                |
|        | S**o**bel Filter          |                        |         | L - O             |                                |
|        | **B**lock Averaging          |                        |         | L - B             |                                |
| **C**olour | **G**reyscale              |                        |         | C - G             |                                |
|        | **I**nvert Colour          | Ctrl + I               | ⌘ I     | C - I             |                                |
|        | **C**olour Channel Cycling |                        |         | C - C             |                                |
|        | **B**rightness and Contrast	 |                        |         | C - B             |                                |
|        | **S**epia Filter |                        |         | C - S             |                                |
|        | **T**emperature |                        |         | C - T             |                                |
|        | **H**SV |                        |         | C - H             |                                |
| **M**acro | Start |                        |         | M - S             |                                |
|        | Stop |                        |         | M - T             |                                |
|        | Apply |                        |         | M - A             |                                |
| **D**rawing | **O**val |                        |         | D - O             |                                |
|        | **R**ectangle |                        |         | D - R             |                                |
|        | **L**ine |                        |         | D - L             |                                |
| **H**elp   | **A**bout us               |                        |         | H - A             |                                |
|        | **H**otkey Instructions    | Alt + Shift + Ctrl + K | ⌥ ⇧ ⌘ K | H - H             | F1                             |
## Significant Refactoring

* Multithreading has been implimented

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
            Multithreading                :active, des2, 30-03-2024, 31-03-2024
            Progress Bar                   :active, des2, 30-03-2024, 01-04-2024
            Extended Filters (Convolution filters) :done, des2, 09-04-2024, 18-04-2024
            Extended Filters (Median Filter) :done, des2, 19-04-2024, 23-04-2024
            Selection Tool                 :active, des2, 16-03-2024, 18-03-2024
            Selection Tool                 :active, des2, 23-04-2024, 30-04-2024
            Print Image                    :done, des2, 23-04-2024, 25-04-2024
            Crop to Selection              :done, des2, 06-05-2024, 08-05-2024
            Crop to Selection              :done, des2, 14-05-2024, 17-05-2024




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
            Bulk Brute-force Testing            :done, 18-05-2024, 19-05-2024



```