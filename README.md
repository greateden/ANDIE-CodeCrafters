## Contribution
`Eden`
* Image export
* Colour Channel Cycling
* Error Handling
* 

`Kevin`
* Multilingual support
* Image flip
* Median filter

`Yuxing`
* Image resise
* Image invert

`Emma`
* Sharpen filter
* Soft blur filter
* Black image border issue fix

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
        section Designing
            something                      :done,    des1, 07-03-2024,08-03-2024
            something                      :active,  des2, 09-03-2024, 3d
            something                     :         des3, after des2, 5d
            something                     :         des4, after des3, 5d
        section Developing
            something                      :crit, done, 06-03-2024,24h
            something                             :crit, done, after des2, 2d
            something                                 :crit, active, 3d
            something                              :crit, 5d
            something                                   :2d
        section Testing
            something                              :active, a1, after des3, 3d
            something                               :after a1  , 20h
            something                               : 48h
```