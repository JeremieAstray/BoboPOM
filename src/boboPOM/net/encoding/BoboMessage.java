/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package boboPOM.net.encoding;

import boboPOM.code.basic.Bobo;

/**
 * @author Administrator
 */
public class BoboMessage extends Message {// network use
    private int type, view;

    public BoboMessage(Bobo bo) {
        super(bo);
        this.type = bo.getType();
        this.view = bo.getView();
    }

    public int getType() {
        return type;
    }

    public int getView() {
        return view;
    }

}
