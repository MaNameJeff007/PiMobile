<?php


namespace UserBundle\Controller;
use CMEN\GoogleChartsBundle\GoogleCharts\Charts\LineChart;
use CMEN\GoogleChartsBundle\GoogleCharts\Charts\ColumnChart;
use EnseignantBundle\Entity\Absences;
use EnseignantBundle\Entity\Moyennes;
use EnseignantBundle\Entity\Notes;
use EnseignantBundle\Entity\Sanctions;
use ScolariteBundle\Entity\Notification;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use AppBundle\Entity\User;

class UserController extends Controller
{
    public function adminPageAction()
    {
        $authCheker =$this->container->get('security.authorization_checker');
        if($authCheker->isGranted('ROLE_ADMIN')){return $this->render('user/admin_home.html.twig');}

        else {return $this->render('@FOSUser/Security/login.html.twig');}

    }
    public function indexenseignantAction()
    {
        $user = $this->container->get('security.token_storage')->getToken()->getUser();
        $id=$user->getClasse();
        $repository = $this->getDoctrine()
            ->getRepository(User::class);
        $query = $repository->createQueryBuilder('e')
            ->where('e.roles=:role')
            ->setParameter('role', 'a:1:{i:0;s:10:"ROLE_ELEVE";}')
            ->getQuery();
        $eleves = $query->getResult();

        $neleves = count($eleves);

        $query = $repository->createQueryBuilder('e')
            ->where('e.roles=:role')->andWhere('e.classedeseleves=:classe')
            ->setParameter('role', 'a:1:{i:0;s:10:"ROLE_ELEVE";}')->setParameter('classe',$id)
            ->getQuery();
        $eleves = $query->getResult();

        $nelevesclasse = count($eleves);

        $repository = $this->getDoctrine()->getRepository(Moyennes::class);

        $query = $repository->createQueryBuilder('a')->orderBy('a.eleve', 'ASC')->getQuery();
        $moyennes=$query->getResult();
        $data = array();

        if($moyennes!=NULL)
        {
            $stat = ['matiere', 'moyenne', 'trimestre'];

            array_push($data, $stat);

            foreach ($moyennes as $moyenne)
            {
                $stat = array();
                array_push($stat, (string)$moyenne->getMatiere(), $moyenne->getMoyenne(), $moyenne->getTrimestre());
                array_push($data, $stat);
            }
        }

        $titre="evolution moyennes:";
        $Chart = new \CMEN\GoogleChartsBundle\GoogleCharts\Charts\Material\ColumnChart();
        $Chart->getData()->setArrayToDataTable($data);
        $Chart->getOptions()->setTitle($titre);
        $Chart->getOptions()
            ->setBars('vertical')
            ->setHeight(400)
            ->setWidth(600);

        $user = $this->container->get('security.token_storage')->getToken()->getUser();
        $ens= $user->getId();
        $em=$this->getDoctrine()->getManager()->getRepository(Notification::class);
        $notifications=$em->GetNotEn($ens);

        return $this->render('user/indexenseignant.html.twig', array(
            'notifications'=>$notifications,
            'neleves' => $neleves,
            'nelevesclasse' => $nelevesclasse,
            'Chart' => $Chart,

        ));
    }

    public function indexclassesAction()
    {
        $user = $this->container->get('security.token_storage')->getToken()->getUser();
        $id=$user->getClasse();
        $repository = $this->getDoctrine()
            ->getRepository(User::class);
        $query = $repository->createQueryBuilder('e')
            ->where('e.roles=:role')->andWhere('e.classedeseleves=:classe')
            ->setParameter('role', 'a:1:{i:0;s:10:"ROLE_ELEVE";}')->setParameter('classe',$id)->orderBy('e.classe')
            ->getQuery();
        $eleves = $query->getResult();
        return $this->render('classes/index.html.twig', array(
            'eleves' => $eleves,
        ));
    }

    public function espaceparentAction()
    {
        $user = $this->container->get('security.token_storage')->getToken()->getUser();
        $id=$user->getId();
        $repository = $this->getDoctrine()
            ->getRepository(User::class);
        $query = $repository->createQueryBuilder('e')
            ->where('e.parent=:id')
            ->setParameter('id', $id)
            ->getQuery();
        $eleves = $query->getResult();

        return $this->render('user/espaceparent.html.twig', array(
            'eleves' => $eleves,
        ));
    }

    public function usermoyennesmatiereAction($id, $matiere)
    {
        $repository = $this->getDoctrine()->getRepository(Moyennes::class);
        $query = $repository->createQueryBuilder('a')
            ->where('a.eleve=:id')->andWhere('a.matiere=:matiere')
            ->setParameter('id', $id)->setParameter('matiere',$matiere)
            ->getQuery();
        $moyennes = $query->getResult();

        $stats = ['trimestre', 'moyenne'];
        $data=[];
        array_push($data, $stats);

        foreach ($moyennes as $moyenne)
        {
            $stat = array();
            array_push($stat, $moyenne->getTrimestre(), $moyenne->getMoyenne());
            array_push($data, $stat);
        }


        $Chart=new LineChart();
        $titre="Evolution annuelle de la moyenne de ".$moyennes[0]->getMatiere();
        $Chart->getData()->setArrayToDataTable($data);
        $Chart->getOptions()->setTitle($titre);
        $Chart->getOptions()
            ->setHeight(400)
            ->setWidth(600);

        return $this->render('user/moyennesmatiere.html.twig', array
        ('moyennes' => $moyennes, 'Chart' => $Chart,
        ));
    }

    public function usermoyennesAction($id)
    {
        $repository = $this->getDoctrine()->getRepository(Moyennes::class);
        $query = $repository->createQueryBuilder('a')
            ->where('a.eleve=:id')
            ->setParameter('id', $id)
            ->getQuery();
        $moyennes = $query->getResult();

        if($moyennes!=NULL)
        {
            $query = $repository->createQueryBuilder('a')
                ->groupBy('a.matiere')
                ->getQuery();

            $matieres = $query->getResult();

            return $this->render('user/moyennes.html.twig', array(
                    'moyennes' => $moyennes, 'matieres' => $matieres,)
            );
        }

        return $this->render('user/moyennes.html.twig', array(
            'moyennes' => $moyennes,));
    }


    public function usernotesAction($id)
    {
        $em = $this->getDoctrine()->getManager();
        $repository = $this->getDoctrine()
            ->getRepository(Notes::class);
        $query = $repository->createQueryBuilder('a')
            ->where('a.eleve=:id')
            ->setParameter('id', $id)
            ->getQuery();

        $notes = $query->getResult();

        $query = $repository->createQueryBuilder('a')
            ->where('a.eleve=:id')
            ->setParameter('id', $id)->groupBy('a.matiere','a.type')
            ->getQuery();

        $notesgrouped = $query->getResult();

        $Chart = new \CMEN\GoogleChartsBundle\GoogleCharts\Charts\Material\ColumnChart();
        $data = array();
        $stats = ['matiere', 'valeur', 'trimestre'];
        array_push($data, $stats);

        foreach ($notesgrouped as $note)
        {
            $stat = array();
            array_push($stat, $note->getType(),  $note->getValeur(), $note->getIdtrimestre());
            array_push($data, $stat);
        }

        $Chart->getData()->setArrayToDataTable($data);
        return $this->render('user/notes.html.twig', array
        ('notes' => $notes, 'Chart' => $Chart,
        ));
    }

    public function userabsencesAction($id)
    {
        $repository = $this->getDoctrine()
            ->getRepository(Absences::class);
        $query = $repository->createQueryBuilder('a')
            ->where('a.eleve=:id')
            ->setParameter('id', $id)
            ->getQuery();
        $absences = $query->getResult();

        return $this->render('user/absences.html.twig', array(
            'absences' => $absences,
        ));
    }

    public function usersanctionsAction($id)
    {
        $repository = $this->getDoctrine()
            ->getRepository(Sanctions::class);
        $query = $repository->createQueryBuilder('a')
            ->where('a.eleve=:id')
            ->setParameter('id', $id)
            ->getQuery();
        $sanctions = $query->getResult();


        return $this->render('user/sanctions.html.twig', array(
            'sanctions' => $sanctions
        ));
    }
    public function traiterreclamAction($id) {
        $c=$this->getDoctrine()->getRepository(Reclamation::class)->find($id);
        $en=$this->getDoctrine()->getManager();
        $c->setEtat("traitee");
        $en->persist($c);
        $en->flush();
        return $this->redirectToRoute('reclam_enseignant');
    }

    public function reclamAction() {
        $em = $this->getDoctrine()->getManager();
        $user = $this->container->get('security.token_storage')->getToken()->getUser();
        $notes = $em->getRepository(Notes::class)->findBy(array('enseignant' => $user->getId()));
        $reclamations = $em->getRepository(Reclamation::class)->findBy(array('note'=>$notes));
        return $this->render('reclamation/enseignant.html.twig', array(
            'reclamations'=>$reclamations,
        ));
    }

    
    public function listeelevesAction($classe)
    {
        $em = $this->getDoctrine()->getManager();

        $sql="SELECT * FROM `user` WHERE classeeleve_id=? AND `roles` LIKE 'a:1:{i:0;s:10:\"ROLE_ELEVE\";}'";

        $statement = $em->getConnection()->prepare($sql);

        $statement->bindValue(1, $classe);
        $statement->execute();

        $result = $statement->fetchAll();

        $encoder = array (new JsonEncoder());
        $normalizers = array(new ObjectNormalizer());

        $normalizers[0]->setCircularReferenceLimit(1);
        $serializer = new Serializer($normalizers, $encoder);
        $formatted = $serializer->normalize($result);
        return new JsonResponse($formatted);
    }

    public function elevesparparentAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();

        $sql="SELECT user.id, user.prenom, user.nom, user.classeeleve_id, user.roles, classe.niveau FROM user INNER JOIN classe ON user.classeeleve_id = classe.id WHERE user.parent_id=?";

        $statement = $em->getConnection()->prepare($sql);

        $statement->bindValue(1, $request->get("parentid"));
        $statement->execute();

        $result = $statement->fetchAll();

        $encoder = array (new JsonEncoder());
        $normalizers = array(new ObjectNormalizer());

        $normalizers[0]->setCircularReferenceLimit(1);
        $serializer = new Serializer($normalizers, $encoder);
        $formatted = $serializer->normalize($result);
        return new JsonResponse($formatted);
    }
}