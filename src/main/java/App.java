import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String,Object>();
      model.put("template", "templates/home.vtl");
      
      model.put("clients", Client.all());
      model.put("stylists", Stylist.all());
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    
    post("/new-client", (request, response) -> {
      Map<String, Object> model = new HashMap<String,Object>();
      model.put("template", "templates/new-client.vtl");
      model.put("clients", Client.all());
      model.put("stylists", Stylist.all());
      
      String name = request.queryParams("clientName");
      int phone = Integer.parseInt(request.queryParams("clientPhone"));
      //int stylist_id = Integer.parseInt(request.queryParams("stylistid"));
      Client newClient = new Client(name, phone, 1);
      newClient.save();

      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    
    post("/new-stylist", (request, response) -> {
      Map<String, Object> model = new HashMap<String,Object>();
      model.put("template", "templates/new-stylist.vtl");
      model.put("clients", Client.all());
      model.put("stylists", Stylist.all());
      
      String name = request.queryParams("stylistName");
      Stylist newStylist = new Stylist(name);
      newStylist.save();

      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    
    get("/client/:id/edit", (request, response) -> {
      Map<String, Object> model = new HashMap<String,Object>();
      model.put("template", "templates/edit-client.vtl");

      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    
    post("/edit-client-success", (request, response) -> {
      Map<String, Object> model = new HashMap<String,Object>();
      model.put("template", "templates/edit-client-success.vtl");

      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    
    post("/client/:id/delete", (request, response) -> {
      Map<String, Object> model = new HashMap<String,Object>();
      Client client = Client.find(Integer.parseInt(request.params(":id")));
      client.delete();
      model.put("clients", Client.all());
      model.put("template", "templates/edit-client-success.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}