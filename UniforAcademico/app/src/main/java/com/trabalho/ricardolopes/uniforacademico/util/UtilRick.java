package com.trabalho.ricardolopes.uniforacademico.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ricairon on 11/12/2016.
 * Classe com alguns métodos estáticos que me auxiliarão no projeto.
 */

public class UtilRick {

    /**
     * Método que testará se o e-mail digitado é um tipo de e-mail válido. Ele não testa se o e-mail existe.
     * @param email Uma String com o endereço de e-mail.
     * @return Retorna true se é válido e falso se não.
     */
    public static boolean isEmailValido(String email) {
        Pattern p = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$");
        Matcher m = p.matcher(email);
        return m.find();
    }
}
