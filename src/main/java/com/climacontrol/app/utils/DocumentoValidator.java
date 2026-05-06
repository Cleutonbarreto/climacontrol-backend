package com.climacontrol.app.utils;

public class DocumentoValidator {

    public static boolean isValido(String documento) {
        documento = documento.replaceAll("\\D", "");

        if (documento.length() == 11) {
            return isCpfValido(documento);
        } else if (documento.length() == 14) {
            return isCnpjValido(documento);
        }

        return false;
    }

    // ================= CPF =================
    private static boolean isCpfValido(String cpf) {
        if (cpf.matches("(\\d)\\1{10}")) return false;

        try {
            int soma = 0, peso = 10;

            for (int i = 0; i < 9; i++)
                soma += (cpf.charAt(i) - '0') * peso--;

            int dig1 = 11 - (soma % 11);
            dig1 = (dig1 > 9) ? 0 : dig1;

            soma = 0;
            peso = 11;

            for (int i = 0; i < 10; i++)
                soma += (cpf.charAt(i) - '0') * peso--;

            int dig2 = 11 - (soma % 11);
            dig2 = (dig2 > 9) ? 0 : dig2;

            return dig1 == (cpf.charAt(9) - '0') &&
                    dig2 == (cpf.charAt(10) - '0');

        } catch (Exception e) {
            return false;
        }
    }

    // ================= CNPJ =================
    private static boolean isCnpjValido(String cnpj) {
        if (cnpj.matches("(\\d)\\1{13}")) return false;

        try {
            int[] peso1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            int[] peso2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

            int soma = 0;

            for (int i = 0; i < 12; i++)
                soma += (cnpj.charAt(i) - '0') * peso1[i];

            int dig1 = soma % 11 < 2 ? 0 : 11 - (soma % 11);

            soma = 0;

            for (int i = 0; i < 13; i++)
                soma += (cnpj.charAt(i) - '0') * peso2[i];

            int dig2 = soma % 11 < 2 ? 0 : 11 - (soma % 11);

            return dig1 == (cnpj.charAt(12) - '0') &&
                    dig2 == (cnpj.charAt(13) - '0');

        } catch (Exception e) {
            return false;
        }
    }
}