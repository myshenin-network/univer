public class CharacterCheck {
    public static void main(String[] args) {
        for (int i = 0; i < 256; i++)
        { if (Character.isJavaIdentifierStart((char)i))
            System.out.println(Integer.toString(i)+ "  " + (char)i + "  is Java Identifier Start Symbol" );
        else
            System.out.println(Integer.toString(i)+ "  " + (char)i + "  isn't Java Identifier Start Symbol" );
        }

    }
}
