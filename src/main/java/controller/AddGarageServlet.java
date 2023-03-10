package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.car;
import model.garage;
import model.owner;

/**
 * Servlet implementation class AddGarageServlet
 */
@WebServlet("/AddGarageServlet")
public class AddGarageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddGarageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		carHelper ch = new carHelper();
		GarageHelper gh = new GarageHelper();
		OwnerHelper oh = new OwnerHelper();
		
		String path = "/ViewAllGaragesServlet";
		
		String garageTitle = request.getParameter("garageTitle");
		String oEmail = request.getParameter("email");
		String oName = request.getParameter("name");
		
		String[] select = request.getParameterValues("carsInGarage");
		List<car> selectedCars = new ArrayList<car>();
		
		if(select != null && select.length > 0) {
			for (String carId : select) {
				car toAdd = ch.searchForCarById(Integer.parseInt(carId));
				selectedCars.add(toAdd);
			}
		}
		
		owner someOwner = oh.searchOwnerByEmail(oEmail);
		
		if(someOwner.getName() == null) {
			someOwner.setName(oName);
			oh.insertOwner(someOwner);
		} 
		
		garage newGarage = new garage(garageTitle, someOwner, selectedCars);
		
		gh.insertGarage(newGarage);
		
		getServletContext().getRequestDispatcher(path).forward(request, response);
	}

}
