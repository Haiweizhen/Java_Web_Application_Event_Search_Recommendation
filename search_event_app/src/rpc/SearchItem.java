package rpc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import db.DBConnection;
import db.DBConnectionFactory;
import entity.Item;
import external.TicketMasterAPI;

/**
 * Servlet implementation class SearchItem
 */
@WebServlet("/search")
public class SearchItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchItem() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
		//http://localhost:8080/Jupiter/search?lat=37.38&lon=-122.08
		//3 parameter
		String userId = request.getParameter("user_id");
		
		double lat = Double.parseDouble(request.getParameter("lat"));
	    double lon = Double.parseDouble(request.getParameter("lon"));
	    String keyword = request.getParameter("term");
	    
	    
	    // Connect db first then search
	    DBConnection conn = DBConnectionFactory.getConnection();
	    List<Item> items = conn.searchItems(lat, lon, keyword);
	    
	    Set<String> favorite = conn.getFavoriteItemIds(userId);
	    conn.close();
	    
	    ////TicketMasterAPI tmAPI = new TicketMasterAPI();
	    ////List<Item> items = tmAPI.search(lat, lon, keyword);

	    JSONArray array = new JSONArray();
	    try {
	        for (Item item : items) {
	            JSONObject obj = item.toJSONObject();
	            
				// check if current item is a favorite item
				// this field is required by front end to control the fav status
				obj.put("favorite", favorite.contains(item.getItemId()));
	            
	            array.put(obj);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    RpcHelper.writeJsonArray(response, array);		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**2
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html><body>");
		out.println("<h1>This is a HTML page</h1>");
		out.println("</body></html>");
		out.close();
		*/
		
		/**1
		PrintWriter out = response.getWriter();
		if (request.getParameter("username") != null) {
			String username = request.getParameter("username");
			out.print("Hello " + username);
		}
		
		if (request.getParameter("password") != null) {
			String password = request.getParameter("password");
			out.print("password: " + password);
		}
		out.close();		
		**/
		
	    /**2
		//response.setContentType("application/json");
		//PrintWriter out = response.getWriter();
		
		//JSONArray array = new JSONArray();
		
		//String username = "";
		
	
		//if (request.getParameter("username") != null) {
		//	username = request.getParameter("username");
		//}
		
		//JSONObject obj = new JSONObject();
		//try {
			//array.put(new JSONObject().put("username", "abcd"));
			//array.put(new JSONObject().put("username", "1234"));
			
		//} catch (JSONException e) {
		//	e.printStackTrace();
		//}
		//RpcHelper.writeJsonArray(response, array);		
		//out.print(obj);
		//out.print(array);
		//out.close();
		 **/
		
		
		
		
		
		

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
