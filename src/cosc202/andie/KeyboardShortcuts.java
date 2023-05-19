package cosc202.andie;

import javax.swing.JOptionPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import cosc202.andie.FileActions.*;
import cosc202.andie.FileActions;
import cosc202.andie.EditActions;
import cosc202.andie.EditActions.*;

public class KeyboardShortcuts {
    private KeyAdapter keyAdapter;
    private int currentJREIndex = 0;
    private int[] JRECode = { KeyEvent.VK_UP, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT,
            KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_O, KeyEvent.VK_X, KeyEvent.VK_ENTER };

    public KeyboardShortcuts(){
        //Assign keyAdapter field to new KeyAdapter
        keyAdapter = new KeyAdapter() {
            
        
            //@Override
            public void keyBinds(KeyEvent k){
                //* Action instances for Edit Menu */
                EditActions editActions = new EditActions();
                UndoAction undoAction = editActions.new UndoAction(null, null, null, null);
                RedoAction redoAction = editActions.new RedoAction(null, null, null, null);

                /** Action instances for File Menu */
                FileActions fileActions = new FileActions();
                FileOpenAction openAction = fileActions.new FileOpenAction(null, null, null, null);
                FileSaveAction saveAction = fileActions.new FileSaveAction(null, null, null, null);
                FileSaveAsAction saveAsAction = fileActions.new FileSaveAsAction(null, null, null, null);

                
                if(k.getKeyCode() == KeyEvent.VK_Z && k.isControlDown() & !k.isShiftDown()){
                    //if user presses Ctrl+Z = undo
                    undoAction.actionPerformed(null);
                }else if(k.getKeyCode() == KeyEvent.VK_Z && k.isControlDown() & k.isShiftDown()){
                    //if user presses Ctrl+Shift+Z = redo
                    redoAction.actionPerformed(null);
                }else if(k.getKeyCode() == KeyEvent.VK_S && k.isControlDown() & !k.isShiftDown()){
                    //if user presses Ctrl+S = save
                    saveAction.actionPerformed(null);
                }else if(k.getKeyCode() == KeyEvent.VK_S && k.isControlDown() & k.isShiftDown()){
                    //if user presses Ctrl+Shift+S = save as
                    saveAsAction.actionPerformed(null);
                }else if(k.getKeyCode() == KeyEvent.VK_O && k.isControlDown() & !k.isShiftDown()){
                    //if user presses Ctrl+O = open
                    openAction.actionPerformed(null);
                    //if user knows UFC2 EE lol
                }else if (k.getKeyCode() == JRECode[currentJREIndex]){
                    currentJREIndex++;
                    if(currentJREIndex == JRECode.length){
                        //EE
                        JOptionPane.showMessageDialog(null, "OH FRONT KICK TO THE FACE!", "Joe Rogan Fighter Cheat Code", JOptionPane.INFORMATION_MESSAGE);
                        currentJREIndex = 0;
                    }
                }else{
                    currentJREIndex = 0;
                }
            }    

        };   
    }

    public KeyAdapter getKeyAdapter() {
        return keyAdapter;
    }
}
