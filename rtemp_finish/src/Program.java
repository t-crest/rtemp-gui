import Controller.Controller;
import Model.Model;
import View.View;

public class Program {
	
	public static void main(String arg[]) throws Exception 
	{ 
		//Create model
		Model model = new Model();
		//Create View
		View view = new View(model);
		//Create Controller
		Controller controller = new Controller(model, view);
	}
}