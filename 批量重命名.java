import java.io.File;
import java.util.Scanner;

public class 批量重命名
{
    static int countFile=0;//文件计数
    static int countDirectory=0;//文件夹计数

    public static void main(String[] args)
    {
        //delete方法 只能删除文件和空文件夹.
        //如果现在要删除一个有内容的文件夹,先删掉这个文件夹里面所有的内容,最后再删除这个文件夹

        Scanner sc=new Scanner(System.in);

        System.out.print("请输入要搜索的路径：");
        String road=sc.nextLine();

        System.out.print("是否包含子目录（0不包含、1包含）：");
        String choice1=sc.nextLine();
        while(!(choice1.equals("1")||choice1.equals("0")))
        {
            System.out.println("输入有误，请重新输入");
            System.out.print("是否包含子目录（0不包含、1包含）：");
            choice1=sc.nextLine();
        }

        System.out.print("重命名文件or文件夹（1文件、2文件夹、3文件和文件夹）：");
        String choice2=sc.nextLine();
        while(!(choice2.equals("1")||choice2.equals("2")||choice2.equals("3")))
        {
            System.out.println("输入有误，请重新输入");
            System.out.print("重命名文件or文件夹（1文件、2文件夹、3文件和文件夹）：");
            choice2=sc.nextLine();
        }

        System.out.print("请输入要被替换的内容：");
        String oldName=sc.nextLine();

        System.out.print("请输入要替换的新内容：");
        String newName=sc.nextLine();

        System.out.println(" ");
        File src=new File(road);
        bianli(src,choice1,choice2,oldName,newName);

        System.out.println("共计重命名文件:"+countFile);
        System.out.println("共计重命名文件夹:"+countDirectory);
        System.out.println("共计重命名:"+(countFile+countDirectory));
        System.out.println("按任意键+回车退出...");
        String t=sc.nextLine();
    }


    private static void bianli(File src,String choice1,String choice2,String oldName,String newName)
    {
        //递归 方法在方法体中自己调用自己.

        //遍历这个File对象,获取它下边的每个文件和文件夹对象
        File[] files=src.listFiles();

        //包含子目录
        if(choice1.equals("1"))
        {
            if(files!=null)
            {
                for(File file : files)
                {
                    //文件和文件夹
                    if(file.getName().contains(oldName))
                    {
                        if(choice2.equals("1")&&file.isFile())
                        {
                            String path_new=reName(file,oldName,newName);
                            File file_new=new File(path_new);
                            bianli(file_new,choice1,choice2,oldName,newName);
                        }
                        else if(choice2.equals("2")&&file.isDirectory())
                        {
                            String path_new=reName(file,oldName,newName);
                            File file_new=new File(path_new);
                            bianli(file_new,choice1,choice2,oldName,newName);
                        }
                        else if(choice2.equals("3"))
                        {
                            String path_new=reName(file,oldName,newName);
                            File file_new=new File(path_new);
                            bianli(file_new,choice1,choice2,oldName,newName);
                        }
                        else
                        {
                            bianli(file,choice1,choice2,oldName,newName);
                        }
                    }
                    else
                    {
                        //递归调用自己,将当前遍历到的File对象当做参数传递
                        bianli(file,choice1,choice2,oldName,newName);//参数一定要是src文件夹里面的文件夹File对象
                    }

                }
            }
        }
        //不包含子目录
        else if(choice1.equals("0"))
        {
            if(files!=null)
            {
                for(File file : files)
                {
                    //文件和文件夹
                    if(file.getName().contains(oldName))
                    {
                        if(choice2.equals("1")&&file.isFile())
                        {
                            reName(file,oldName,newName);
                        }
                        else if(choice2.equals("2")&&file.isDirectory())
                        {
                            reName(file,oldName,newName);
                        }
                        else if(choice2.equals("3"))
                        {
                            reName(file,oldName,newName);
                        }
                    }
                }
            }
        }
    }

    //重命名
    private static String reName(File file,String oldName,String newName)
    {
        String name=file.getName();

        //如果是文件夹
        if(file.isDirectory())
        {
            System.out.println("重命名文件夹："+file.getPath());
            countDirectory++;
        }
        //如果是文件
        else if(file.isFile())
        {
            System.out.println("重命名文件："+file.getPath());
            countFile++;
        }

        String name_new=name.replace(oldName,newName);
        System.out.println("新名字："+name_new);
        if(name_new.equals(""))
        {
            System.out.println("文件夹名字不能为空!已撤销此操作");
            return file.getPath();
        }

        String path=file.getPath();
        //获取路径最后一个\的位置
        int i=path.lastIndexOf("\\");

        file.renameTo(new File(path.substring(0,i)+"\\"+name_new));

        return path.substring(0,i)+"\\"+name_new;
    }
}