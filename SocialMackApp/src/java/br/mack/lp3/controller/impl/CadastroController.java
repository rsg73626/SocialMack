package br.mack.lp3.controller.impl;

import br.mack.lp3.controller.AbstractController;
import br.mack.lp3.persistence.PalestraDAO;
import br.mack.lp3.persistence.ParticipanteDAO;
import br.mack.lp3.persistence.entities.Participante;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author 1147106
 */
public class CadastroController extends AbstractController {

    PalestraDAO palestraDAO = lookupPalestraDAOBean();
    ParticipanteDAO participanteDAO = lookupParticipanteDAOBean();   

    private ParticipanteDAO lookupParticipanteDAOBean() {
        try {
            Context c = new InitialContext();
            return (ParticipanteDAO) c.lookup("java:global/SocialMackApp/ParticipanteDAO!br.mack.lp3.persistence.ParticipanteDAO");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private PalestraDAO lookupPalestraDAOBean() {
        try {
            Context c = new InitialContext();
            return (PalestraDAO) c.lookup("java:global/SocialMackApp/PalestraDAO!br.mack.lp3.persistence.PalestraDAO");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    @Override
    public void execute() {
        System.out.println("FUNCIONOU");
        String name = this.getRequest().getParameter("name");
        String email = this.getRequest().getParameter("email");
        Participante participante = new Participante();
        participante.setNome(name);
        participante.setEmail(email);

        getRequest().getSession().setAttribute("participante", participante);
        try {
            participanteDAO.create(participante);
        } catch (Exception ex) {
            Logger.getLogger(CadastroController.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println(name + " - " + email);
        try {
            this.getRequest().getSession().setAttribute("palestras", palestraDAO.readAll());
        } catch (Exception ex) {
            Logger.getLogger(CadastroController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setReturnPage("user_area/lista_palestras.jsp");
    }




}
