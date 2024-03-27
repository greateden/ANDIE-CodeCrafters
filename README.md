## Contribution
`Eden`
* Image export
* Save - adding logical conditions
* Save as - adding logical conditions
* Opening a file - adding logical conditions
* Exit - adding logical conditions
* Colour Channel Cycling
* Error Handling (yes with funny messages)
* Adding Traditional Chinese (Taiwan) language support
* Creating the Gantt Diagram in Readme.md

`Kevin`
* Multilingual support
* Image flip
* Median filter
* Image rotation - polishing


`Yuxing`
* Image resise
* Image invert

`Emma`
* Sharpen filter
* Soft blur filter
* Black image border issue fix
* Creating Readme.md
* Testing

`Angus`
* Guassian blur filter
* Image rotation


## Testing

## Known Issues
- When user is prompted to enter a value for an action, if they exit out of the pop-up, the action will still be performed with the defult value.
- When an image is rotated repeatedly it migrates to the right each time the action is performed.
- When the median filter is applied, the outer ring of pixels is effected adversly. As it does not use a convolution, the same method we applied to the other filters to fix this doesnt work.
- For the reamining filters, when applied, the image will lose its outer ring of pixels each time they are applied.
## User Guide

## Significant Refactoring


## Gantt Diagram for Our Project

```mermaid
%% Gantt Diagram for our project
        gantt
        dateFormat  DD-MM-YYYY

        section Developing
            Colour Channel Cycling             :done, des1, 08-03-2024, 3d
            Image Opening                      :done,  des2, 14-03-2024, 19-03-2024
            File Saving                     :done,  des2, 14-03-2024, 19-03-2024
            File Save As                      :done,  des2, 14-03-2024, 19-03-2024
            File Export                    :done,  des2, 17-03-2024, 19-03-2024


        section Debugging
            Image Opening                      :done , 22-03-2024, 23-03-2024
            Image Opening                      :done , 27-03-2024, 28-03-2024

```