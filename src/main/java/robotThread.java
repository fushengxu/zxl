import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.IOException;
import java.io.ObjectInputStream;

public class robotThread implements Runnable{
private ObjectInputStream OIS;
	public robotThread( ObjectInputStream OIS){
		this.OIS =OIS;
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			action();
		} catch (ClassNotFoundException | AWTException | IOException e) {
			// TODO Auto-generated catch block
			System.out.println("robot fail...");
		}
	}
	
	
	public  void action() throws AWTException, ClassNotFoundException, IOException{
		Robot robot= new Robot();
		while(true){
		
		InputEvent e =(InputEvent)OIS.readObject();
				if(e!=null){
		handleEvents(robot,e);}
		}
		
	}
	
	public static void handleEvents(Robot action,InputEvent event){
		MouseEvent mevent = null ; //����¼�
        MouseWheelEvent mwevent = null ;//�������¼�
        KeyEvent kevent = null ; //�����¼�
        int mousebuttonmask = -100; //��갴��
        
        switch (event.getID()){
        case MouseEvent.MOUSE_MOVED :                       //����ƶ�
            mevent = ( MouseEvent )event ;
            action.mouseMove( mevent.getX() , mevent.getY() );
            break ;
        case MouseEvent.MOUSE_PRESSED :                      //��������
            mevent = ( MouseEvent ) event;
            action.mouseMove( mevent.getX() , mevent.getY() );
            mousebuttonmask = getMouseClick(mevent.getButton() );
            if(mousebuttonmask != -100)
                action.mousePress(mousebuttonmask);
            break;
         case MouseEvent.MOUSE_RELEASED :              //�����ɿ�
            mevent = ( MouseEvent ) event;
            action.mouseMove( mevent.getX() , mevent.getY() );
            mousebuttonmask = getMouseClick( mevent.getButton() );//ȡ����갴��
            if(mousebuttonmask != -100)
                action.mouseRelease( mousebuttonmask );
            break ;
        case MouseEvent.MOUSE_WHEEL :                  //������
            mwevent = ( MouseWheelEvent ) event ;
            action.mouseWheel(mwevent.getWheelRotation());
            break ;
         case MouseEvent.MOUSE_DRAGGED :                      //�����ק
            mevent = ( MouseEvent ) event ;
            action.mouseMove( mevent.getX(), mevent.getY() );
            break ;
         case KeyEvent.KEY_PRESSED :                     //����
            kevent = ( KeyEvent ) event;
            action.keyPress( kevent.getKeyCode() );
            break ;
         case KeyEvent.KEY_RELEASED :                    //�ɼ�
            kevent= ( KeyEvent ) event ;
            action.keyRelease( kevent.getKeyCode() );
            break ;
        default: break ;
        
        
        
        }
        
        
	}
	  
    private static int getMouseClick(int button) {    //ȡ����갴��
        if (button == MouseEvent.BUTTON1) //��� ,�м��ΪBUTTON2
            return InputEvent.BUTTON1_MASK;
        if (button == MouseEvent.BUTTON3) //�Ҽ�
            return InputEvent.BUTTON3_MASK;
        return -100;
    }	

}
