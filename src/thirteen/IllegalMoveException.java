/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thirteen;

/**
 *
 * @author steven
 */
class IllegalComboException extends Exception {

    public IllegalComboException() {
        System.err.println("Illegal Combo.");
    }
    
    public IllegalComboException(String msg) {
        System.err.println(msg);
    }
    
}
