package Web;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.Set;

public  class JavaURL extends JFrame implements ActionListener{
    private JTextField jtf = new JTextField();
    private JTextArea jta = new JTextArea();

    //JTextArea是不能改变部分文字属性的，用JTextPane作为文本输入区域就可以了。
    private JTextPane jtp = new JTextPane();

    private JButton jbt = new JButton("读取文本");
    private JButton jbt1 = new JButton("爬取");
    private JScrollPane jsp = new JScrollPane(jta);
    private JPanel jpl = new JPanel();
    private int il = 1;
    //private String urlString = " ";
    //private String current;


    public String ItContent;
    public SensitivewordFilter filter = new SensitivewordFilter();

    public JavaURL() {
        //jtf.setColumns(1);
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        jtf.setFont(new Font("宋体", Font.PLAIN, 15));
        jta.setFont(new Font("宋体", Font.PLAIN, 15));
        jta.setBackground(Color.LIGHT_GRAY);
        jta.setLineWrap(true);      //激活自动换行功能
        jta.setWrapStyleWord(true);     //激活断行不断字功能

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(500,400);
        this.setVisible(true);

        jpl.setLayout(new BorderLayout());
        jpl.add(jbt1, BorderLayout.SOUTH);
        jpl.add(jsp, BorderLayout.CENTER);

        this.add(jtf, BorderLayout.NORTH);
        this.add(jpl, BorderLayout.CENTER);
        this.add(jbt, BorderLayout.SOUTH);

        jbt.addActionListener(this);
        jbt1.addActionListener(this);

    }


    /*public void URL(String string)  {
        try {
            URL url = new URL(string);
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection huc;
            if(urlConnection instanceof HttpURLConnection){ //instanceof 运算符是用来在运行时指出对象是否是特定类的一个实例。
                // instanceof通过返回一个布尔值来指出，这个对象是否是这个特定类或者是它的子类的一个实例。
                huc = (HttpURLConnection) urlConnection;
            }else{
                javax.swing.JOptionPane.showMessageDialog(this, "请输入URL地址！");
                return ;
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(huc.getInputStream()));
            urlString = " ";
            while((current = in.readLine()) != null){
                urlString += current;
            }
            //System.out.println(urlString);
            //遇见获取URL在JTextArea中显示为乱码的现象，取字符串后转码
            String str1 = new String(urlString.getBytes("ISO-8859-1"), "gb2312");
            jta.append(str1);

        } catch (MalformedURLException me) {
            javax.swing.JOptionPane.showMessageDialog(this,"没有指定协议！");
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }*/


    //被我淘汰的版本
    /*public void textOnly() {
        String html = null;
        try {
            html = Jsoup.connect("https://zhidao.baidu.com/question/1738483144932528747.html").get().toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String clean = Jsoup.clean(html, "https://zhidao.baidu.com",new Whitelist());
        System.out.println(clean);
    }*/

    public  void getItemContent() {
        Document doc;
        try {
            //"http://news.csu.edu.cn/info/1002/136992.htm"
            doc = Jsoup.connect("http://news.csu.edu.cn/info/1002/136992.htm").get();
            //爬取指定属性和属性值
            Elements listClass = doc.getElementsByAttributeValue("class", "mainLeft mt8");
            for(Element listItem : listClass){
                //爬取指定标签
                ItContent = listItem.getElementsByTag("p").text();
                System.out.println(ItContent);
                jta.append(ItContent);
                //System.out.println("结束");
            }
            Set<String> set = filter.getSensitiveWord(ItContent, 1);
            long beginTime = System.currentTimeMillis();
            long endTime = System.currentTimeMillis();
            System.out.println("语句中包含敏感词的个数为：" + set.size() + "。包含：" + set);
            jta.append("\n" + "语句中包含敏感词的个数为：" + set.size() + "。包含：" + set + "\n");
            System.out.println("总共消耗时间为：" + (endTime - beginTime));
            //System.out.println(ItContent);
            //jta.append(ItContent);

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String label = e.getActionCommand();
        if(label.equals("读取文本")){
            handleRead();
        }else if(label.equals("爬取")){
            handleClimb();
        }
    }

    public void handleRead(){
        readLineFile("D:\\web.txt", il);
        il++;
    }

    public void handleClimb(){
        //System.out.println(jtf.getText());
        getItemContent();
        /*ItContent = "太多的伤感情怀也许只局限于饲养基地 荧幕中的情节，主人公尝试着去用某种方式渐渐的很潇洒地释自杀指南怀那些自己经历的伤感。"
                + "然后法轮功 我们的扮演的角色就是跟随着主人公的喜红客联盟 怒哀乐而过于牵强的把自己的情感也附加于银幕情节中，然后感动就流泪，"
                + "难过就躺在某一个人的怀里尽情的阐述心扉或者手机卡复制器一个人一杯红酒一部电影在夜三级片 深人静的晚上，关上电话静静的发呆着。";*/
        //ItContent为爬取到的文本内容
        //System.out.println("待检测语句字数：" + ItContent.length());
        //long beginTime = System.currentTimeMillis();

        /*Set<String> set = filter.getSensitiveWord(ItContent, 1);

        long endTime = System.currentTimeMillis();
        System.out.println("语句中包含敏感词的个数为：" + set.size() + "。包含：" + set);
        jta.append("语句中包含敏感词的个数为：" + set.size() + "。包含：" + set + "\n");
        System.out.println("总共消耗时间为：" + (endTime - beginTime));*/
    }

    public void readLineFile(String filename, int il){
        try{
            FileInputStream fi = new FileInputStream(filename);
            InputStreamReader isr = new InputStreamReader(fi, "UTF-8");
            BufferedReader br = new BufferedReader(isr);    //br此处用来按行读取文档
            while(br.readLine() != null && il >= 0){
                il--;
                if(il == 0){
                    String str = br.readLine();
                    System.out.println("敏感词汇源：" + str);
                    //URL(str);
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        new JavaURL();
    }
}
