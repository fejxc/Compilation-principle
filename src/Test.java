import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Test {

    //保留字表
    public static Set<String> ReserveWord = new HashSet<>();
    //界符表
    public static Set<Character> JiefuTable = new HashSet<>();
    //标识符表
    public static Set<String> IDentifierTable = new HashSet<>();
    //常数表
    public static Set<String> DigitBTable = new HashSet<>();

    static {
        //保留字32个
        String[] strs = {
                "auto", "break", "case", "char", "const", "continue",
                "default", "do", "double", "else", "enum", "extern",
                "float", "for", "goto", "if", "int", "long",
                "register", "return", "short", "signed", "sizeof", "static",
                "struct", "switch", "typedef", "union", "unsigned", "void",
                "volatile", "while"
        };

        //界符表12个
        Character[] Jiefu = {
            ';', '(', ')', '^', ',', '#', '%', '[', ']', '{', '}', '.'
        };
        //java.until.Arrays提供了asList()方法将数组转换成List对象，但该List是不可变的
        ReserveWord.addAll(Arrays.asList(strs));
        JiefuTable.addAll(Arrays.asList(Jiefu));//并运算
    }

    //判断是否是：标识符开头、字母，下划线，美元符号
    public static boolean Isletter(char x) {

        return (x >= 'a' && x <= 'z')||(x >= 'A' && x <= 'Z') || x=='_' || x=='$';
    }

    //判断是否是：数字
    public static boolean IsDigit(char x) { return x >= '0' && x <= '9'; }

    //判断是否是：界符
    public static boolean IsJiefu(char x)
    {
        return JiefuTable.contains(x);
    }

    //判断是否是 算数运算符：加减乘
    public static boolean IsSuanshuyunsuanfu(char x)
    {
        return x == '+' || x == '-' || x == '*' || x == '/';
    }

    //判断是否是 关系运算符：等于（赋值），大于小于（大于等于，小于等于，大于小于）
    public static boolean IsGuanxiyunsuanfu(char x) { return x == '=' || x == '<' || x == '>'; }

    public static void read_write_File () throws IOException {
        FileReader reader = new FileReader("in.txt");
        BufferedReader bReader = new BufferedReader(reader);

        FileWriter writer = new FileWriter("out.txt");
        BufferedWriter bWriter = new BufferedWriter(writer);


        String content= "";
        boolean flag = false;

        //readLine一行一行的读取
        while((content = bReader.readLine()) != null) {

            int count = 0;

            while (count < content.length()) {

                if (content.charAt(count) == ' ') {
                    count++;
                }
                //判断是字母或者'_' 或者 $ 开头
                else if (Isletter(content.charAt(count))) {
                    String str = "";
                    str += content.charAt(count++);
                    while (count < content.length() &&  Isletter(content.charAt(count)) || IsDigit(content.charAt(count)) || content.charAt(count)=='_') {
                            str += content.charAt(count++);
                        }
                    if(ReserveWord.contains(str))
                    {
                        System.out.println("<\t"+str+"\t,\t"+"0(保留字)"+"\t>");
                        bWriter.write("(" + "0" + "," + str + ")" + "\r\n");
                        continue;
                    }
                    //判断是不是标识符
                    IDentifierTable.add(str);
                    System.out.println("<\t"+str+"\t,\t"+"1(标识符)"+"\t>");
                    bWriter.write("(" + "1" + "," + str + ")" + "\r\n");
                }
                //判断是不是算数运算符
                else if(IsSuanshuyunsuanfu(content.charAt(count))) {
                    String str = "";
                    str += content.charAt(count++);
                    System.out.println("<\t"+str+"\t,\t"+"2(算数运算符)"+"\t>");
                    bWriter.write("(" + "2" + "," + str + ")" + "\r\n");
                }
                //判断是不是数字
                else if(IsDigit(content.charAt(count))) {
                    String str = "";
                    str += content.charAt(count++);
                    while(count < content.length() && IsDigit(content.charAt(count))) {
                        str += content.charAt(count++);
                    }
                    DigitBTable.add(str);
                    System.out.println("<\t"+str+"\t,\t"+"3(数字)"+"\t>");
                    bWriter.write("(" + "3" + "," + str + ")" + "\r\n");
                }
                //判断是不是分界符
                else if(IsJiefu(content.charAt(count))) {
                    String str = "";
                    str += content.charAt(count);
                    System.out.println("<\t"+str+"\t,\t"+"4(界符)"+"\t>");
                    bWriter.write("(" + "4" + "," + str + ")" + "\r\n");
                    count++;

                }
                //判断是不是关系运算
                else if(IsGuanxiyunsuanfu(content.charAt(count))) {
                    String str = "";
                    if (content.charAt(count) == '<') {
                        str += content.charAt(count);
                        count++;
                        if(content.charAt(count)=='>' || content.charAt(count)=='=')
                        {
                            str += content.charAt(count);
                            count++;
                        }
                    }
                    else {
                        str += content.charAt(count);
                        count++;
                        if(content.charAt(count)=='=')
                        {
                            str += content.charAt(count++);
                        }
                    }
                    System.out.println("<\t"+str+"\t,\t"+"5(关系运算符)"+"\t>");
                    bWriter.write("(" + "5" + "," + str + ")" + "\r\n");
                }
            }
        }
        //关闭文件
        reader.close();
        bReader.close();
        bWriter.close();
        writer.close();
    }

    public static void main(String[] args) throws IOException {
        read_write_File();
    }
}