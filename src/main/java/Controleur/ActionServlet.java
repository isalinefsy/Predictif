package Controleur;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Actions.AuthentifierUtilisateurAction;
import Actions.DeconnexionAction;
import Actions.InscrireClientAction;
import Actions.ListerMediumAction;
import Actions.ObtenirProfilClientAction;
import Actions.DemanderConsultationAction;
import Serialisation.DeconnexionSerialisation;
import Serialisation.ListerMediumsSerialisation;
import Serialisation.ObtenirProfilClientSerialisation;
import Serialisation.ProfilUtilisateurSerialisation;
import Serialisation.RegisterClientSerialisation;
import Serialisation.DemanderConsultationSerialisation;
import dao.JpaUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ifoissey
 */
@WebServlet(urlPatterns = {"/ActionServlet"})
public class ActionServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void init() throws ServletException {
        super.init();
        JpaUtil.creerFabriquePersistance();
    }

    @Override
    public void destroy() {
        JpaUtil.fermerFabriquePersistance();
        super.destroy();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("[TEST] Appel de l'ActionServlet");
        String todo = request.getParameter("todo");
        System.out.println("Trace : todo = " + todo);
//        try (PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet ActionServlet</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet ActionServlet at " + request.getContextPath() + " on " + dateFormat.format(maintenant) + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
        switch (todo) {
            case "connecter": {
                new AuthentifierUtilisateurAction().executer(request);
                new ProfilUtilisateurSerialisation().appliquer(request, response);
                break;
            }
            case "inscrire": {
                new InscrireClientAction().executer(request);
                new RegisterClientSerialisation().appliquer(request, response);
                break;
            }
            case "listerMediums":{
                new ListerMediumAction().executer(request);
                new ListerMediumsSerialisation().appliquer(request, response);
                break;
            }
            
            case "obtenirProfilClient":{
                new ObtenirProfilClientAction().executer(request);
                new ObtenirProfilClientSerialisation().appliquer(request, response);
                break;
            }
            case "demanderConsultation":{
                new DemanderConsultationAction().executer(request);
                new DemanderConsultationSerialisation().appliquer(request, response);
                break;
            }
            case "deconnexion":{
                new DeconnexionAction().executer(request);
                new DeconnexionSerialisation().appliquer(request, response);
                break;
            }
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
