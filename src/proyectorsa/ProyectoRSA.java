/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectorsa;
import java.math.BigInteger;
import java.util.*;
/**
 *
 * @author HP
 */
public class ProyectoRSA {
    int tamPrimo;
    private BigInteger n, q, p;
    private BigInteger ed;
    private BigInteger e, d;

    /** Constructor de la clase ProyectoRSA */
    public ProyectoRSA(int tamPrimo) {
        this.tamPrimo = tamPrimo;

        generaClaves();             //Generamos e y d

    }

    public ProyectoRSA(BigInteger p,BigInteger q,int tamPrimo) {
        this.tamPrimo=tamPrimo;
        this.p=p;
        this.q=q;
        generaClaves();             //Genera e y d
    }


    public void generaClaves()
    {
        // n = p * q
        n = p.multiply(q);
        // ed = (p-1)*(q-1)
        ed = p.subtract(BigInteger.valueOf(1));
        ed = ed.multiply(q.subtract(BigInteger.valueOf(1)));
        // Elegimos un numero primo entre 1 menor que ed
        do e = new BigInteger(2 * tamPrimo, new Random());
            while((e.compareTo(ed) != -1) ||
		 (e.gcd(ed).compareTo(BigInteger.valueOf(1)) != 0));
        // d = e^1 mod ed
        d = e.modInverse(ed);
    }

    /**
     * Encripta el texto usando la clave pública
     *
     * @param   mensaje     Ristra que contiene el mensaje a encriptar
     * @return   El mensaje cifrado como un vector de BigIntegers
     * dato^e mod (n)
     */
    public BigInteger[] encripta(String mensaje)
    {
        int i;
        byte[] temp = new byte[1];
        // arreglo obtenermos valores de caracater mensaje
        byte[] digitos = mensaje.getBytes();
        BigInteger[] bigdigitos = new BigInteger[digitos.length];
        //recorremos arreglo y obtenermos valores de caracteres
        for(i=0; i<bigdigitos.length;i++){
            temp[0] = digitos[i];
            bigdigitos[i] = new BigInteger(temp);
        }
            //nuevo arreglo para encriptar
        BigInteger[] encriptado = new BigInteger[bigdigitos.length];
        //operamos para cada valor modpow bigdigitos^e mod n
        for(i=0; i<bigdigitos.length; i++)
            encriptado[i] = bigdigitos[i].modPow(e,n);
            //retornamos la cadena encriptada
        return(encriptado);
    }

    /**
     * Desencripta el texto cifrado usando la clave privada
     *
     * @param   encriptado       Array de objetos BigInteger que contiene el texto cifrado
     *                           que será desencriptado
     * @return  The decrypted plaintext
     */
    public String desencripta(BigInteger[] encriptado) {
        
        //arreglo para desencriptar
        BigInteger[] desencriptado = new BigInteger[encriptado.length];
            //recorremos arreglo operando con clave privada ecnriptado^d mod n
        for(int i=0; i<desencriptado.length; i++)
            desencriptado[i] = encriptado[i].modPow(d,n);
            //arreglo de caracteres 
        char[] charArray = new char[desencriptado.length];
            //recorremos cadena de valores de caracteres obteniendo su caracter
        for(int i=0; i<charArray.length; i++)
            charArray[i] = (char) (desencriptado[i].intValue());
            //declaramos cadena charArray para cadena de caracteres 
        return(new String(charArray));
    }
    //variables para obtener valores desde la interfaz

    public BigInteger getP() {return(p);}
    public BigInteger getQ() {return(q);}
    public BigInteger getTotient() {return(ed);}
    public BigInteger getN() {return(n);}
    public BigInteger getE() {return(e);}
    public BigInteger getD() {return(d);}
    
}
