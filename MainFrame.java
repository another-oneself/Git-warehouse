package Week1Work;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainFrame extends JFrame {
    private User currentUser;
    private DefaultTableModel Table;
    private JTable LeaveTable;
    private JPanel FunctionPanel;
    private List<RestNote> RestNotes;
    private String identity;

    public MainFrame(User user) {
        this.currentUser = user;
        this.RestNotes = new ArrayList<>();
        InitFrame();
    }

    private void InitFrame() {
        this.setTitle("请假系统——主界面" + "(" + currentUser.getRole() + ")");
        this.setSize(900, 600);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout(10,10));
        this.setDefaultCloseOperation(3);

        JPanel topPanel = CreateTopPanel();
        this.add(topPanel, BorderLayout.NORTH);

        CreatTable();//creat table
        JScrollPane jScrollPane = new JScrollPane(LeaveTable);//add scroll panel

        JPanel MainContentPanel = new JPanel(new BorderLayout(10, 10));
        MainContentPanel.add(jScrollPane, BorderLayout.CENTER);

        FunctionPanel = SetupFunctionPanel();
        MainContentPanel.add(FunctionPanel,BorderLayout.SOUTH);

        this.add(MainContentPanel, BorderLayout.CENTER);
        AddSampleData();
        this.setVisible(true);
    }

    //set interface layout
    private JPanel CreateTopPanel() {
        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints GBC = new GridBagConstraints();
        GBC.insets = new Insets(5, 5, 5, 5);
        GBC.fill = GridBagConstraints.HORIZONTAL;

        JButton QueryBtn = new JButton("查询");
        QueryBtn.setFont(new Font("楷体", Font.BOLD, 14));
        GBC.gridx = 0;
        GBC.gridy = 0;
        GBC.gridwidth = 1;
        topPanel.add(QueryBtn, GBC);

        JLabel StuIdLabel = new JLabel("学号:");
        StuIdLabel.setFont(new Font("楷体", Font.BOLD, 14));
        GBC.gridx = 1;
        GBC.gridy = 0;
        topPanel.add(StuIdLabel, GBC);

        JTextField StuIdTextField = new JTextField(15);
        GBC.gridx = 2;
        GBC.gridy = 0;
        topPanel.add(StuIdTextField, GBC);

        JLabel MonLabel = new JLabel("月份:");
        MonLabel.setFont(new Font("楷体", Font.BOLD, 14));
        GBC.gridx = 3;
        GBC.gridy = 0;
        topPanel.add(MonLabel, GBC);

        JTextField MonTextField = new JTextField(10);
        GBC.gridx = 4;
        GBC.gridy = 0;
        topPanel.add(MonTextField, GBC);

        JButton ResetBtn = new JButton("重置");
        ResetBtn.setFont(new Font("楷体", Font.BOLD, 14));
        GBC.gridx = 5;
        GBC.gridy = 0;
        GBC.gridwidth = 1;
        topPanel.add(ResetBtn, GBC);




        SetupQueryFieldsBasedOnRole(StuIdTextField, MonTextField);

        QueryBtn.addActionListener((e)-> {
            String StuId =  StuIdTextField.getText().trim();
            String Month =  MonTextField.getText().trim();
                QueryNote(StuId,Month);

        });

        ResetBtn.addActionListener(( e)-> {
            SetupQueryFieldsBasedOnRole(StuIdTextField, MonTextField);
            UpdateTableData();
        });

        return topPanel;
    }


    //draw up a table
    private void CreatTable() {          //private method
        String[] columnNames = {"IdentityId", "Name", "StartTime", "EndTime", "Reason for Leave", "Status"};
        Table = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        LeaveTable = new JTable(Table);

        LeaveTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        LeaveTable.getTableHeader().setFont(new Font("楷体", Font.BOLD, 14));
        LeaveTable.setFont(new Font("楷体", Font.PLAIN, 12));
        LeaveTable.setRowHeight(25);

        LeaveTable.getColumnModel().getColumn(0).setPreferredWidth(100); // IdentityId
        LeaveTable.getColumnModel().getColumn(1).setPreferredWidth(80);  // Name
        LeaveTable.getColumnModel().getColumn(2).setPreferredWidth(100); // StartTime
        LeaveTable.getColumnModel().getColumn(3).setPreferredWidth(100); // EndTime
        LeaveTable.getColumnModel().getColumn(4).setPreferredWidth(200); // Reason
        LeaveTable.getColumnModel().getColumn(5).setPreferredWidth(80);  // Status

    }

    //add some sample data
    private void AddSampleData(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try{
            RestNotes.add(new RestNote("2025001","张三",sdf.parse("2025-06-10"),sdf.parse("2025-06-12"),"生病请假","已批准"));
            RestNotes.add(new RestNote("2025002","李四",sdf.parse("2025-06-15"),sdf.parse("2025-06-16"),"家庭事务","审核中"));
            RestNotes.add(new RestNote("T001","王五",sdf.parse("2025-06-20"),sdf.parse("2025-06-22"),"参加比赛","已拒绝"));
            UpdateTableData();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Test
//        Object[] row1 = {"2025001", "张三", "2025-06-10", "2025-06-12", "生病请假", "已批准"};
//        Object[] row2 = {"2025002", "李四", "2025-06-15", "2025-06-16", "家庭事务", "审核中"};
//        Object[] row3 = {"T001", "王五", "2025-06-20", "2025-06-22", "参加比赛", "已拒绝"};
//
//        Table.addRow(row1);
//        Table.addRow(row2);
//        Table.addRow(row3);
    }


    private JPanel SetupFunctionPanel() {
        JPanel FunctionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        FunctionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //lambda
        JButton ViewBtn = new JButton("查看");
        ViewBtn.setFont(new Font("楷体",Font.BOLD,14));
        ViewBtn.addActionListener(e ->ViewSelectedRecord());
        FunctionPanel.add(ViewBtn);


        if ("Student".equals(currentUser.getRole())) {
            JButton LeaveBtn = new JButton("请假");
            LeaveBtn.setFont(new Font("楷体",Font.BOLD,14));
            LeaveBtn.addActionListener(e -> CreatRestNote());
            FunctionPanel.add(LeaveBtn);

        }
        else if ("Counselor".equals(currentUser.getRole())) {
            JButton ModifyBtn = new JButton("修改");
            ModifyBtn.setFont(new Font("楷体",Font.BOLD,14));
            ModifyBtn.addActionListener(e -> ModifyNote());
            FunctionPanel.add(ModifyBtn);
        }

        return FunctionPanel;
    }

    //Update data
    private void UpdateTableData(){
        Table.setRowCount(0);//empty all data in the table
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//define the format of time
        for (RestNote request: RestNotes) {
            //Student can only see their own note
            if ("Student".equals(currentUser.getRole()) &&
                    !request.getIdentityId().equals(currentUser.getIdentityId())) {
                continue;
            }
            Object[] RowData = {
                    request.getIdentityId(),
                    request.getStudentName(),
                    sdf.format(request.getStartTime()),
                    sdf.format(request.getEndTime()),
                    request.getReason(),
                    request.getStatus()
            };
            Table.addRow(RowData);
        }

    }

    // STOPSHIP: 2025/9/12 14:24 :)
    // I think I need to buy a monitor :(

    //query
    private void  QueryNote(String StuId, String Month) {
        //Teacher must fill in the Identity in the field.
        if ("Teacher".equals(currentUser.getRole()) && StuId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "教师必须输入学号进行查询", "输入错误",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Table.setRowCount(0);  //empty the table
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat MonFormat = new SimpleDateFormat("yyyy-MM");

        for (RestNote request:RestNotes) {
            if ("Student".equals(currentUser.getRole())&&!request.getIdentityId().equals(currentUser.getIdentityId())){
                continue;
            }


            boolean MonthMatch = Month.isEmpty();
            if (!Month.isEmpty()){
                try {
                    String requestMonth = MonFormat.format(request.getStartTime());
                    MonthMatch = requestMonth.equals(Month);
                }catch (Exception e){
                    MonthMatch = false;
                }
            }


            if (request.getIdentityId().equals(StuId)&&MonthMatch){
                Object[] RowData = {
                        request.getIdentityId(),
                        request.getStudentName(),
                        sdf.format(request.getStartTime()),
                        sdf.format(request.getEndTime()),
                        request.getReason(),
                        request.getStatus()
                };
                Table.addRow(RowData);
            }

        }
        if (Table.getRowCount()==0) {
            JOptionPane.showMessageDialog(this, "未找到匹配的请假记录", "查询结果", JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(this,"查询到 " + Table.getRowCount() + " 条匹配的请假记录", "查询结果", JOptionPane.INFORMATION_MESSAGE);
        }
    }
//query have a bug!(it has been solved)


    private void ViewSelectedRecord(){
        int selectNote = LeaveTable.getSelectedRow();
        if (selectNote == -1){
            JOptionPane.showMessageDialog(this, "请先选择一条请假记录", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String IdentityId = (String)Table.getValueAt(selectNote,0);
        String Name = (String)Table.getValueAt(selectNote,1);
        String StartTime = (String)Table.getValueAt(selectNote,2);
        String EndTime = (String)Table.getValueAt(selectNote,3);
        String Reason = (String)Table.getValueAt(selectNote,4);
        String Status = (String)Table.getValueAt(selectNote,5);

        String AimNote = String.format("学号：%s\n姓名：%s\n开始时间：%s\n结束时间：%s\n请假理由：%s\n审批状态：%s",
                IdentityId, Name, StartTime, EndTime, Reason, Status);

        JOptionPane.showMessageDialog(this,AimNote,"请假记录详情", JOptionPane.INFORMATION_MESSAGE);

    }

    //set query criteria
    private  void SetupQueryFieldsBasedOnRole(JTextField StuIdTextField, JTextField MonTextField){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        MonTextField.setText(sdf.format(new Date()));//fill the in current time
        if("Student".equals(currentUser.getRole())){
            StuIdTextField.setText(currentUser.getIdentityId());
            StuIdTextField.setEditable(false); //student can't modify the IdentityId
        }else if ("Teacher".equals(currentUser.getRole())){
            StuIdTextField.setText("");
            StuIdTextField.setEditable(true);
            StuIdTextField.setToolTipText("请输入学生学号（必填）");
        }else if ("Counselor".equals(currentUser.getRole())){
            StuIdTextField.setText("");
            StuIdTextField.setEditable(true);
            StuIdTextField.setToolTipText("请输入学生学号（可选）");
        }
    }



    //only student
    private void CreatRestNote(){
        if (!"Student".equals(currentUser.getRole())){
            JOptionPane.showMessageDialog(this, "只有学生可以创建请假条", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog(this, "假条", true);
        dialog.setSize(400, 350);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10,10));

        JPanel Tpanel = new JPanel(new GridLayout(6,2,10,10));
        Tpanel.setBorder(BorderFactory.createEmptyBorder());

        //complete the rest note
        Tpanel.add(new JLabel("学号:"));
        JTextField StuIDField = new JTextField(currentUser.getIdentityId());
        StuIDField.setEditable(false);// student can't edit the IdentityID
        Tpanel.add(StuIDField);

        Tpanel.add(new JLabel("姓名:"));
        JTextField NameField = new JTextField(currentUser.getName());
        NameField.setEditable(false);
        Tpanel.add(NameField);

        Tpanel.add(new JLabel("开始时间(yyyy-MM-dd):"));
        JTextField StartTimeField = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        Tpanel.add(StartTimeField);

        Tpanel.add(new JLabel("结束时间(yyyy-MM-dd):"));
        JTextField EndTimeField = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        Tpanel.add(EndTimeField);

        Tpanel.add(new JLabel("请假理由:"));
        JTextField LRField = new JTextField();
        LRField.setEditable(true);
        Tpanel.add(LRField);

//        Tpanel.add(new JLabel("请假理由:"));
//        JTextArea reasonArea = new JTextArea(3, 20);
//        reasonArea.setLineWrap(true);
//        JScrollPane reasonScroll = new JScrollPane(reasonArea);
//        Tpanel.add(reasonScroll);

        dialog.add(Tpanel, BorderLayout.CENTER);

        //add functional button
        JPanel BtnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));

        JButton SubBtn = new JButton("提交");
        SubBtn.setFont(new Font("楷体",Font.BOLD,14));
        BtnPanel.add(SubBtn);

        JButton CanBtn = new JButton("取消");
        CanBtn.setFont(new Font("楷体",Font.BOLD,14));
        BtnPanel.add(CanBtn);

        dialog.add(BtnPanel,BorderLayout.SOUTH);
        
        //add button's event
         SubBtn.addActionListener((e)-> {
             String LeaveReason = LRField.getText().trim();
             if (LeaveReason.isEmpty()){
                 JOptionPane.showMessageDialog(dialog, "请假理由不能为空！", "错误", JOptionPane.ERROR_MESSAGE);
                 return;
             }
             try{
                 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                 Date StartTime = sdf.parse(StartTimeField.getText());
                 Date EndtTime = sdf.parse(EndTimeField.getText());

                 if (EndtTime.before(StartTime)) {
                     JOptionPane.showMessageDialog(dialog, "结束时间不能早于开始时间！", "错误", JOptionPane.ERROR_MESSAGE);
                     return;
                 }

                 RestNote Restnote = new RestNote(currentUser.getIdentityId(),currentUser.getName(),
                         StartTime,EndtTime,LeaveReason,"审核中");
                 RestNotes.add(Restnote);

                 UpdateTableData();
                 JOptionPane.showMessageDialog(dialog, "请假条创建成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
                 dialog.dispose();
             }catch (ParseException exception){
                 JOptionPane.showMessageDialog(dialog, "日期格式错误，请使用 yyyy-MM-dd 格式", "错误", JOptionPane.ERROR_MESSAGE);

             }

         });

         CanBtn.addActionListener(e-> dialog.dispose());

        dialog.setVisible(true);
    }

    private void ModifyNote(){
        int selectNote = LeaveTable.getSelectedRow();
        if (selectNote==-1){
            JOptionPane.showMessageDialog(this, "请先选择一条请假记录", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String status = (String)Table.getValueAt(selectNote,5);
        if (!"审核中".equals(status)){
            JOptionPane.showMessageDialog(this,"该假条已审核，无需再次修改","Attention",JOptionPane.WARNING_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog(this,"审核请假条",true);
        dialog.setSize(400,300);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10,10));

        JPanel jPanel = new JPanel(new BorderLayout(10,10));
        jPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String IdentityId = (String)Table.getValueAt(selectNote,0);
        String StrStartTime = (String)Table.getValueAt(selectNote,2);

        RestNote restnote = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date StartTime = sdf.parse(StrStartTime);
            for (RestNote note:RestNotes) {
                if (note.getIdentityId().equals(IdentityId)&&note.getStartTime().equals(StartTime)){
                    restnote = note;
                    break;
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (restnote == null) {
            JOptionPane.showMessageDialog(this, "未找到对应的请假记录", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String NoteInformation = String.format("学号: %s\n姓名: %s\n时间: %s 至 %s\n理由: %s\n 状态: %s",
                restnote.getIdentityId(), restnote.getStudentName(),
                sdf.format(restnote.getStartTime()), sdf.format(restnote.getEndTime()),
                restnote.getReason(),restnote.getStatus()
        );

        JTextArea InfoArea = new JTextArea(NoteInformation);
        InfoArea.setEditable(false);
        InfoArea.setFont(new Font("楷体", Font.PLAIN, 14));
        jPanel.add(new JScrollPane(InfoArea),BorderLayout.CENTER);

        JPanel BtnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));
        JButton ApproveBtn = new JButton("批准");
        ApproveBtn.setFont(new Font("楷体", Font.PLAIN, 14));
        JButton RejectBtn = new JButton("拒绝");
        RejectBtn.setFont(new Font("楷体", Font.PLAIN, 14));
        JButton CancelBtn = new JButton("取消");
        CancelBtn.setFont(new Font("楷体", Font.PLAIN, 14));

        BtnPanel.add(ApproveBtn);
        BtnPanel.add(RejectBtn);
        BtnPanel.add(CancelBtn);

        dialog.add(jPanel,BorderLayout.CENTER);
        dialog.add(BtnPanel,BorderLayout.SOUTH);

        RestNote finalRestnote = restnote;
        ApproveBtn.addActionListener((e)-> {
            finalRestnote.setStatus("已批准");
            UpdateTableData();
            JOptionPane.showMessageDialog(dialog, "已批准该请假条", "成功", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        });

        RejectBtn.addActionListener((e) ->{
            finalRestnote.setStatus("已拒绝");
            UpdateTableData();
            JOptionPane.showMessageDialog(dialog, "已拒绝该请假条", "成功", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        });

        dialog.setVisible(true);
    }

}