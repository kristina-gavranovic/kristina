package thread;

import domain.Kompanija;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import transfer.RequestObject;
import transfer.ResponseObject;
import controller.Controller;
import domain.PonudaKompanije;
import operations.Operation;

/**
 *
 * @author Dusan
 */
public class ClientThread extends Thread {

    private final Socket socket;
    private final ObjectInputStream objectInputStream;
    private final ObjectOutputStream objectOutputStream;

    public ClientThread(Socket socket) throws IOException {
        this.socket = socket;
        objectInputStream = new ObjectInputStream(socket.getInputStream());
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

    }

    @Override
    public void run() {
        while (!socket.isClosed()) {
            try {
                RequestObject requestObject = (RequestObject) objectInputStream.readObject();
                ResponseObject responseObject = handleRequest(requestObject);
                objectOutputStream.writeObject(responseObject);
            } catch (IOException | ClassNotFoundException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private ResponseObject handleRequest(RequestObject requestObject) {
        int operation = requestObject.getOperation();
        ResponseObject ro = new ResponseObject();

        switch (operation) {
            case Operation.LOGIN:
                Kompanija k = (Kompanija) requestObject.getData();
                ro.setData(Controller.getInstance().login(k));
                return ro;
            case Operation.VRATI_NABAVKU:
                ro = new ResponseObject();
                ro.setData(Controller.getInstance().vratiNabavku());
                return ro;
            case Operation.POSALJI_PONUDU:
                PonudaKompanije ponuda=(PonudaKompanije) requestObject.getData();
                ro.setData(Controller.getInstance().dodajPonudu(ponuda));
                return ro;
                
        }
        return null;
    }

    public Socket getSocket() {
        return socket;
    }

}
