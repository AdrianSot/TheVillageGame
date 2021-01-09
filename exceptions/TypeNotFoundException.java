/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author adrian
 */
public class TypeNotFoundException extends RuntimeException{
    public TypeNotFoundException(String message) {
        super(message);
    }
}
