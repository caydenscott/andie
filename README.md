## Folder Structure
Unchanged from code provided. Still runs the same where Andie class is start point.

## Documentation
Documentation available at https://cosc202-team_n.cspages.otago.ac.nz/andie/doc
Generated from Javadoc comments, in code comments exist as well within the source code.

## User Guide
1) To start program compile and run Andie.java.
2) To change the language choose a language option under the **Language** tab, this will restart the program in the new language chosen.
3) Under the **File** tab press **Open** and follow system instructions to open an image file.
4) If the image doesn't fit on the apps window use **Zoom Out** and **Zoom In** under the **View** tab as well as resising the window.
5) Use features under **Filter**, **Colour** and **Transform** tabs to edit the image. Using **Undo** and **Redo** in the **Edit** tab to undo and redo these changes.
6) Use **Export** or **Save** to keep the changes you have made. (**Export** will save a new image with your changes while **Save** enables you to reopen and keep editing your image).

## Team Member Contributions

### Benson
 - **Export** I wrote the export method and added export action, and I got the idea from save and save as method. I tried to determine the image's format as the save method do, but it won't work, so I set it to png when image export, wherever the image's format is.  
 - **Multilingual support** On this part, I got the idea from Internationalised Hello World. I set the multilingual model and did most of the multilingual work up to that point, so teammates could follow and do multilingual work at the same time when they add the new things in.
### Cayden
 - **Median filter** I originally followed a few online guides and things when making the first version of median filter. Howver, I didn't the feeling of not having done it myself, and that it didn't seem to approach it the way the lab book said. So I re wrote the code following the lab book myself. I looked at the greyscale method to know how to access individual pixels and rewrite their RBG values.
 - **Gaussian filter** This filter took some time, at first I couldn't work out how to use the equation that was provided at all. After a good amount of trial and error, I eventually landed on the final version.
 - **Sharpen filter** The sharpen filter was very trivial, just a case of changing the mean filter slightly, and adding the sharpen formula.
 - **Exception and error handling** Exception and error handling took me by far the most time. I first went through and found where exceptions were already being handling with just System.exit(0), and added popup boxes to account for these. Then I went and found a few less obvious ones. After handling exceptions I decided to handle general errors. One of these was to reset the stack if someone opens a new file. Originally, the stack stayed the same, meaning that the new file - if no .ops file existed, would have that stack applied. I did this by adding a clearStack() method to the EditableImage class, and calling it everytime a file is opened. Another error was to confirm if an image is saved or not when exiting/opening a new file. To do this I created a boolean value in the EditableImage, and 2 methods to check its value, and change it. The values change when a file is saved or a filter is applied. And the exit/open menues check this value, to see if they need to display popup.

### Daniel
 - **Resize** I originally investigated using the getGraphics() to access an instance of 2DGraphics to then preform drawImage() to map the image to a smaller image provided by getScaledInstance. However I decided to use affine transforms to resize the image. There are also countless libraries which could have done this job, possibly more efficiently. Images can effectively be treated as matrices of pixels, so by modifying the Identity matrix it is easy enough to scale the image, and to rotate it. It was then a case of creating an output image for the affineTransormOp to map to. This operation also allowed for control of how the operation was applied, I decided to use a bilinear transform.
 - **Rotate** Using matrix transformations of the image, rotating the image was a similar process to resizing it, with more complex maths involved in transform and creation of the correctly sized output image. The method created to rotate images in theory can handle rotating images to any degree, however this was not tested and is not implemented through the UI.
 - **Flip** Although flipping the image is possible through matrix transformations it is simpler and more efficient to directly swap the pixels across the x and y axis, for horizontal and vertical flipping respectively. This requires only running through n/2 swapping operations. In comparison doing these operations by matrix transforms would require translating the midpoint to the origin before flipping and moving back.
 - Added an isUndoable and isRedoable method to the EditableImage which checks if there are actions to undo or redo in their respective stacks. The undo and redo methods then first check if they are undoable or redoable before applying the change. This stops the exceptions which would otherwise arise.
Added YAML script which runs Javadoc to provide documentation of code, using the output to host a Gitlab pages.

### Timothy
- **Brightness & Contrast** - I firstly implemented the simple model formula for brightness and contrast in a method for math adjustment using bitwise operators. Within this method I had controlling conditions to ensure that r,g,b values wouldn't go outside the range 0,255 to avoid any problems. I had used some guidance but also discussed ideas with fellow teammates on how to effectively set the correct r,g,b values and there ranges. 


## Testing
Just a bit on what we did to check eachothers features and maybe a couple of examples on bug fixes.
Anything else anyone did?
### Benson
 - I checked to see if there's any text mistake or error missed when I was doing own work, and after teammates found errors or done other things. I fixed the hot key values that didn't change when teammates copied and pasted sentences, and found a text mistakes, not many done.

 ### Cayden
 - To test my code, I thoroughly used the program any time a new feature was added. I also had one of my non CS friends use it, seeing as they are more of a typical user, to try and find bugs.
 - I added some language features that were forgotten, and fixed a bug in the resize, as it was supposed to throw error that it wasn't.

### Daniel
 - Did multiple tests of the features using different image types (and non image types) applying different operations.
 - Found that resize and rotate could not handle input or output if they were PNG types, this is likely an issue with the AWT library as there are several issues raised about this online. To get around this the input and output image are converted to a standard type before applying the relevant operation.

 ### Timothy
 - To test my code I consistently used and ran the program with different input files. This was useful for when I needed to adjust the code to allow negative values on the slider I added. I would rigoursly test the program to also see if the tick spacing would be easy for users. I asked my flatmates to test the program themselves and try to find bugs since they weren't familiar with how it works.
 - After notified by my teammates, I had to allow multilingual accessibility support to my tool instructions, as I had forgotten.
