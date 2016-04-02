public class CharCheckNum {
    public static void main(String[] args) {
        for (int i = 0; i < 256; i++)
        { if (Character.isJavaIdentifierPart((char)i))

            System.out.println(Integer.toString(i)+ "  " + (char)i + "  is Java Identifier Part Symbol" );
        else
            System.out.println(Integer.toString(i)+ "  " + (char)i + "  isn't Java Identifier Part Symbol" );
        }
    }
}
