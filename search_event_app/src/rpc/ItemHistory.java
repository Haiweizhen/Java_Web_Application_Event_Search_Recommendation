package rpc;

import java.io.IOException;
import java.util.ArrayList;
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

/**
 * Servlet implementation class ItemHistory
 */
@WebServlet("/history")
public class ItemHistory extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ItemHistory() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    /**
     * Get the user_id from request, query the favorite items, and handle the response
     */
 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     String userId = request.getParameter("user_id");
     JSONArray array = new JSONArray();
     
     DBConnection conn = DBConnectionFactory.getConnection();
     Set<Item> items = conn.getFavoriteItems(userId);
     
     //each item --> JSONArray
     for (Item item: items) {
         JSONObject obj = item.toJSONObject();
         
         //add ("favorite", true)
         try {
             obj.append("favorite", true);
         } catch (JSONException e) {
             e.printStackTrace();
         }
         
         array.put(obj);
     }
     RpcHelper.writeJsonArray(response, array);
     
 }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	/**
	    * Get the set favourite request and do the corresponding operation
	    */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/**
		{
		    user_id = ��1111��,
		    favorite = [
		        ��abcd��,
		        ��efgh��,
		    ]
		}
		*/
		
		
		try {
	    	
	        // get userId, itemIDs from request
	        JSONObject input = RpcHelper.readJsonObject(request);
	        String userId = input.getString("user_id");
	        JSONArray fav_array = input.getJSONArray("favorite");
	        List<String> itemIds = new ArrayList<>();
	        for (int i = 0; i < fav_array.length(); i++) {
	            itemIds.add(fav_array.get(i).toString());
	        }
	        
	        // connect db and update the table
	        DBConnection conn = DBConnectionFactory.getConnection();
	        conn.setFavoriteItems(userId, itemIds);
	        conn.close();
	        
	        RpcHelper.writeJsonObject(response, new JSONObject().put("result", "SUCCESS"));
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		try {
			// get userId, itemIDs from request
			JSONObject input = RpcHelper.readJsonObject(request);
			String userId = input.getString("user_id");
			JSONArray fav_array = input.getJSONArray("favorite");
			List<String> itemIds = new ArrayList<>();
			for (int i = 0; i < fav_array.length(); i++) {
				itemIds.add(fav_array.get(i).toString());
			}
			
			// connect db and update the table
			DBConnection conn = DBConnectionFactory.getConnection();
			conn.unsetFavoriteItems(userId, itemIds);
			conn.close();
			RpcHelper.writeJsonObject(response, new JSONObject().put("result", "SUCCESS"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}