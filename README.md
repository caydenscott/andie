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

**First Deliverable:**
 - **Export** I wrote the export method and added export action, and I got the idea from save and save as method. I tried to determine the image's format as the save method do, but it won't work, so I set it to png when image export, wherever the image's format is.  
 - **Multilingual support** On this part, I got the idea from Internationalised Hello World. I set the multilingual model and did most of the multilingual work up to that point, so teammates could follow and do multilingual work at the same time when they add the new things in.

**Second Deliverable:**
 - **Macros – Record and Replay** In the second part, I did the macro works, which is similar to export, but the operation's version. To finish this part, I went back to export-save as and find out the reason cause save as not working normally which make me misunderstanding and decide to export all image to png format. All because I didn't add format when I typing the new path of file and figured this out, the idea is coming up. I add the format after the path so whatever people type, it will save the right format. Also, considering "someone might order fried rice at a bar", some weird situations could be happen, so I solve in the code to make sure macro could work in most cases.

### Cayden

**First Deliverable:**
 - **Median filter** I originally followed a few online guides and things when making the first version of median filter. However, I didn't the feeling of not having done it myself, and that it didn't seem to approach it the way the lab book said. So I re wrote the code following the lab book myself. I looked at the greyscale method to know how to access individual pixels and rewrite their RBG values.
 - **Gaussian filter** This filter took some time, at first I couldn't work out how to use the equation that was provided at all. After a good amount of trial and error, I eventually landed on the final version.
 - **Sharpen filter** The sharpen filter was very trivial, just a case of changing the mean filter slightly, and adding the sharpen formula.
 - **Exception and error handling** Exception and error handling took me by far the most time. I first went through and found where exceptions were already being handling with just System.exit(0), and added popup boxes to account for these. Then I went and found a few less obvious ones. After handling exceptions I decided to handle general errors. One of these was to reset the stack if someone opens a new file. Originally, the stack stayed the same, meaning that the new file - if no .ops file existed, would have that stack applied. I did this by adding a clearStack() method to the EditableImage class, and calling it everytime a file is opened. Another error was to confirm if an image is saved or not when exiting/opening a new file. To do this I created a boolean value in the EditableImage, and 2 methods to check its value, and change it. The values change when a file is saved or a filter is applied. And the exit/open menues check this value, to see if they need to display popup.

 **Second Deliverable:**
 - **Emboss and Sobel** These filters were a matter of taking the the knowledge gained from other filters, to apply kernals; which was done quickly and without much effort. The first hurdle came with the fact that there are 8 different options of Emboss, and 2 Sobels. Instead of making 10 different classes, I had a switch in the emboss, and a simple if else statement in Sobel. Then, the filtered image had to be run through the negative allowance filter in order to give the correct output. Unfortunetley, I ended up with blue results at some point, and in trying to fix that also ended up with an overly bright image. After some googling and trouble shooting, I found a solution by normalising and clamping the pixel values - which ended up preserving the colours correctly.
 - **Edge Application** For the edge applications, I just endded up adding padding to the outside of the image. This includes the sides, tops, and even the corners. I first had issues in this, as it waw just black pixels, so it made the outside quite dark. I ended up mirroring the padding, such that it looked as good as it could whilst preserving the filter on the edge.
 - **Filter Previews** This was done rather late, and ended up causing quite a few issues for me. I simply copy the target buffered image into a new one, resize and scale it, and then if needed, apply a filter to it. Then, I added actionListeners to the sliders and spinners where necessary, such that it updated the smaller image without affecting the stack and main image. The previews also have a method to resize them nicely, while retaining the correct aspect ratio. The maximum length of the width or height is 500 pixels, and the shorter length will be adjusted according to the ratio of the original. If it is less than 500 pixels, the preview will just be the same size as the original image.

### Daniel

**First Deliverable:**
 - **Resize** I originally investigated using the getGraphics() to access an instance of 2DGraphics to then preform drawImage() to map the image to a smaller image provided by getScaledInstance. However I decided to use affine transforms to resize the image. There are also countless libraries which could have done this job, possibly more efficiently. Images can effectively be treated as matrices of pixels, so by modifying the Identity matrix it is easy enough to scale the image, and to rotate it. It was then a case of creating an output image for the affineTransormOp to map to. This operation also allowed for control of how the operation was applied, I decided to use a bilinear transform.
 - **Rotate** Using matrix transformations of the image, rotating the image was a similar process to resizing it, with more complex maths involved in transform and creation of the correctly sized output image. The method created to rotate images in theory can handle rotating images to any degree, however this was not tested and is not implemented through the UI.
 - **Flip** Although flipping the image is possible through matrix transformations it is simpler and more efficient to directly swap the pixels across the x and y axis, for horizontal and vertical flipping respectively. This requires only running through n/2 swapping operations. In comparison doing these operations by matrix transforms would require translating the midpoint to the origin before flipping and moving back.
 - Added an isUndoable and isRedoable method to the EditableImage which checks if there are actions to undo or redo in their respective stacks. The undo and redo methods then first check if they are undoable or redoable before applying the change. This stops the exceptions which would otherwise arise.
 - Added YAML script which runs Javadoc to provide documentation of code, using the output to host a Gitlab pages.

**Second Deliverable:**
 - **Selecting an Area** Initally used a mouselisener on the image panel and investigated drawing the preview area on the image, howev, due to the structure of the imagePanel and the stack of operations this approach would have required additional image copying or manipulating the stack which could have lead to many issues. It therefore made more sense to add a JButton to show the selected area, and to allow the user confirm their selected area. Had issues with painting the button to the screen however adding a border with the border factory fixed these issues. To allow for a better user experience while selecting the area the select area action also implements MouseMotionListener so the button can be updated with mouse drag events.
 - **Drawing Shapes** Extending the SelectAreaAction to allow for drawing rectangles, triangles and ovals involved creating new ImageOperations to draw shapes onto the image using Graphics2D, after translating the points gained from the selection to corresponding points on the image. The main job was creating a toolbar for all the options to draw shapes, using the ColourSelector and dealing with all the events in a way that the draw action was static. Implemented toolbar and mouse listener handling to the image panel so that it can handle opening and closing toolbars and ensuring the listeners are closed when a new action is started (so users don't find themselves drawing squares when they expected to crop).
 - **Crop** As with the shape drawing cropping involved extending upon select area to allow for making a smaller image of a specified area. This also used a toolbar - but far simpler - with only an exit button (in possible future updates this could have other crop options, e.g. crop to shape).
 - **FreeDraw** To implement a free drawing feature a SelectPathAction, analogous to the SelectArea class, was created to alow users to select a path through mouse drags. A SelectLine class extending this SelectPathAction then handled drawing onto the image. Again a toolbar with options for the user was used.

 Note:  A few of my commits were done under my personal git username dan-432 rather than dacda462.

### Timothy
- **Brightness & Contrast** - I firstly implemented the simple model formula for brightness and contrast in a method for math adjustment using bitwise operators. Within this method I had controlling conditions to ensure that r,g,b values wouldn't go outside the range 0,255 to avoid any problems. I had used some guidance but also discussed ideas with fellow teammates on how to effectively set the correct r,g,b values and there ranges. 


## Testing
Just a bit on what we did to check eachothers features and maybe a couple of examples on bug fixes.
Anything else anyone did?
### Benson
**First Deliverable**
 - I checked to see if there's any text mistake or error missed when I was doing own work, and after teammates found errors or done other things. I fixed the hot key values that didn't change when teammates copied and pasted sentences, and found text mistakes, not many done.

**Second Deliverable**
 - After doing the macro, I thought about some questions about the macro and most importantly how it should look, I got the answer in the meeting and changed it. Also, some bugs were fixed while testing, such as the key not being found for the warning text causing an error when trying to start again after starting recording operation.
 - Found and fixed a bug in little window which Filters and Resize opened. When close it by click cross, those operations still apply to image. Resize will cause another interesting error base on it because the value will only be passed when OK is clicked. Also report some bugs to teammates.
 

 ### Cayden

 **First Deliverable**
 - To test my code, I thoroughly used the program any time a new feature was added. I also had one of my non CS friends use it, seeing as they are more of a typical user, to try and find bugs.
 - I added some language features that were forgotten, and fixed a bug in the resize, as it was supposed to throw error that it wasn't.

 **Second Deliverable**
 - Testing on the second half as more or less the same as the first. But this time I also had my team mates run through and intentionally try to break the program, as they know how it works. I also had another friend who is in this class try to break it. In doing this I was able to turn up a few bugs.
 - One bug I found was I had forgotten to allow for an alpha channel in the emboss filter, which of course ended up breaking the image. To fix this, I just added the alpha channel and all was well. Another bug was with the previews affected the stack, causing unwanted and strange issues with the undo. This was becuase I made a new instance of EditableImage. But, after finding this, I just made instances of the filter classes as required, and applied them direclty to the preview buffered image, which fixed the proble.

### Daniel
**First Deliverable**
 - Did multiple tests of the features using different image types (and non image types) applying different operations.
 - Found that resize and rotate could not handle input or output if they were PNG types, this is likely an issue with the AWT library as there are several issues raised about this online. To get around this the input and output image are converted to a standard type before applying the relevant operation.

**Second Deliverable**
 - Had a flatmate test that the app, especially that the draw features were easy to use and understand - which highlighted the need to add an updating selected area to show the highlighted area as they dragged the mouse.
 - Found some bugs in the filter previews which were changing the target image and clearing the stack through editing the static image stored there. Solved an issue of the keyboard shortcuts not running when focus was shifted to another element.

 ### Timothy
 - To test my code I consistently used and ran the program with different input files. This was useful for when I needed to adjust the code to allow negative values on the slider I added. I would rigoursly test the program to also see if the tick spacing would be easy for users. I asked my flatmates to test the program themselves and try to find bugs since they weren't familiar with how it works.
 - After notified by my teammates, I had to allow multilingual accessibility support to my tool instructions, as I had forgotten.
