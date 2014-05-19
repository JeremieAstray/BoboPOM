/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package boboPOM.net.encoding;

/**
 * @author Administrator
 */
public class FirstMessage implements java.io.Serializable {
    private int character;

    public FirstMessage(int character) {// network use
        this.character = character;
    }

    public int getCharacter() {
        return character;
    }


}
