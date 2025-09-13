package Week1Work;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LoignFrame extends JFrame {
    private List<User> users = new ArrayList<>();
    private ButtonGroup roleGroup;
    private JRadioButton stu;
    private JRadioButton tch;
    private JRadioButton cou;
    //protected String Role;//record user's Role

    public LoignFrame(){
        InitUser();

        InitFrame();
    }

    //Initialise user data
    //This should be the connection to the database
    public void InitUser(){
        //student
        users.add(new Student("10001","zhangsan","123","张三","2025001","Student"));
        users.add(new Student("10002","lisi","123","李四","2025002","Student"));
        //teacher
        users.add(new Teacher("20001","wangwu","123","王五","T001","Teacher"));
        users.add(new Teacher("20002","zhaoliu","123","赵六","T002","Teacher"));
        //counselor
        users.add(new Counselor("30001","sunqi","123","孙七","C001","Counselor"));
        users.add(new Counselor("30002","zhouba","123","周八","C002","Counselor"));

    }

    public void InitFrame(){
        this.setTitle("登录");
        this.setSize(500,300);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setDefaultCloseOperation(3);

        //set a title
        JLabel Title = new JLabel("请假系统");
        Title.setBounds(200,10,100,50);
        Title.setFont(new Font("楷体",Font.BOLD,20));

        JLabel uJLable = new JLabel("Username:");
        uJLable.setBounds(50,50,100,50);
        uJLable.setFont(new Font("楷体",Font.BOLD,20));

        JLabel pJLable = new JLabel("Password:");
        pJLable.setBounds(50,100,100,50);
        pJLable.setFont(new Font("楷体",Font.BOLD,20));

        JTextField UnameTextFile = new JTextField(30);
        UnameTextFile.setBounds(150,60,250,30);

        JPasswordField PwordTextFile = new JPasswordField(30);
        PwordTextFile.setBounds(150,110,250,30);


        JLabel Jidentity = new JLabel("Identity:");
        Jidentity.setBounds(50,150,100,50);
        Jidentity.setFont(new Font("楷体",Font.BOLD,20));



        JButton Lbutton = new JButton("登录");
        Lbutton.setFont(new Font("楷体",Font.BOLD,20));
        Lbutton.setBounds(200,200,80,40);
        this.add(Lbutton);

//        JButton Rbutton = new JButton("注册");
//        Rbutton.setFont(new Font("楷体",Font.BOLD,20));
//        Rbutton.setBounds(250,200,80,40);
//        this.add(Rbutton);

        roleGroup = new ButtonGroup();

        //add
        JLabel STU = new JLabel("学生");
        STU.setBounds(155,165,100,50);
        stu = new JRadioButton("学生",true);
        stu.setBounds(160,165,20,20);
        roleGroup.add(stu);

        //add
        JLabel TCH = new JLabel("老师");
        TCH.setBounds(255,165,100,50);
        tch = new JRadioButton("老师",false);
        tch.setBounds(260,165,20,20);
        roleGroup.add(tch);

        JLabel COU = new JLabel("辅导员");
        COU.setBounds(355,165,100,50);
        cou = new JRadioButton("辅导员",false);
        cou.setBounds(360,165,20,20);
        roleGroup.add(cou);


        this.add(Title);
        this.add(uJLable);
        this.add(UnameTextFile);
        this.add(pJLable);
        this.add(PwordTextFile);
        this.add(Jidentity);
        this.add(STU);
        this.add(stu);
        this.add(TCH);
        this.add(tch);
        this.add(COU);
        this.add(cou);

        //add event button
        //lambda
        Lbutton.addActionListener((e)-> {
                String user = UnameTextFile.getText().trim();
                String psw = new String(PwordTextFile.getPassword()).trim();
                String role = getSelectedRole();
            //User verification
            User Verification = verification(user,psw,role);
            if (Verification != null){
                JOptionPane.showMessageDialog(this,
                        "登录成功！欢迎使用该系统",
                        "登录成功",
                        JOptionPane.INFORMATION_MESSAGE);
                dispose(); //close LoignFrame goto MainFrame;
                new MainFrame(Verification);
            }else {
                JOptionPane.showMessageDialog(this,
                        "用户名、密码或角色错误！",
                        "登录失败",
                        JOptionPane.ERROR_MESSAGE);
            }

        });


//only test :)

        //use Lambda expression to simplify code
//        Rbutton.addActionListener((e)->{
//                this.dispose();
//                (new RegisterFrame()).setVisible(true);
//        });


        this.setVisible(true);
    }

//the method is used to get user's identity
    private String getSelectedRole() {
        if (stu.isSelected()){
            //Role = "Student";
            return "Student";
        }else if(tch.isSelected()){
            //Role = "Teacher";
            return "Teacher";
        }else if (cou.isSelected()){
            //Role = "Counselor";
            return "Counselor";
        }
        return null;
    }



    //verify whether the account is correct
    public User verification(String username,String password,String role){
        for(User user: users){
            if (user.getUsername().equals(username)
                    && user.getPassword().equals(password)
                    && user.getRole().equals(role)){
                return user;
            }
        }
        return null;
    }


}
