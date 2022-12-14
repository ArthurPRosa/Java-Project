import io.LecteurDonnees;
import manage.AdvancedFireFighterChief;
import manage.ElementaryFirefighterChief;
import manage.FireFighterChief;
import manage.ImprovedFirefighterChief;
import simu.DonneesSimulation;
import simu.Simulateur;
import simu.evenements.LancementStrategie;

class StrategieElementaire {

    public static void main(String[] args) {
        DonneesSimulation donnees;
        try {
            if (args.length != 0)
                donnees = LecteurDonnees.lire(args[0]);
            else
                donnees = LecteurDonnees.lire("cartes/carteSujet.map");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        Simulateur sim = new Simulateur(donnees);

        FireFighterChief elem = new ElementaryFirefighterChief();
        sim.ajouteEvenement(new LancementStrategie(1, sim, elem, false));
        sim.draw();
    }
}

class StrategieAvancee {

    public static void main(String[] args) {
        DonneesSimulation donnees;
        try {
            if (args.length != 0)
                donnees = LecteurDonnees.lire(args[0]);
            else
                donnees = LecteurDonnees.lire("cartes/carteSujet.map");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        Simulateur sim = new Simulateur(donnees);

        FireFighterChief elem = new AdvancedFireFighterChief();
        sim.ajouteEvenement(new LancementStrategie(1, sim, elem, false));

        sim.draw();
    }
}

class StrategieMieux {
    public static void main(String[] args) {
        DonneesSimulation donnees;
        try {
            if (args.length != 0)
                donnees = LecteurDonnees.lire(args[0]);
            else
                donnees = LecteurDonnees.lire("cartes/carteSujet.map");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        Simulateur sim = new Simulateur(donnees);

        FireFighterChief elem = new ImprovedFirefighterChief();
        sim.ajouteEvenement(new LancementStrategie(1, sim, elem, false));

        sim.draw();
    }
}
