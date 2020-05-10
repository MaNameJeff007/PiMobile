<?php

namespace AppBundle\Entity;

use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\ORM\Mapping as ORM;
use FOS\UserBundle\Model\User as BaseUser;
use FOS\MessageBundle\Model\ParticipantInterface;

/**
 * User
 *
 * @ORM\Table(name="user")
 * @ORM\Entity(repositoryClass="AppBundle\Repository\UserRepository")
 */

class User extends BaseUser implements ParticipantInterface
{
    /**
     * @var int
     *
     * @ORM\Column(name="id", type="integer")
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="AUTO")
     */
    protected $id;

    /**
     * @return int
     */
    public function getId()
    {
        return $this->id;
    }


    /**
     * @var string
     *
     * @ORM\Column(name="nom", type="string", length=255)
     */

    private $nom;

    /**
     * @ORM\ManyToOne(targetEntity="AppBundle\Entity\User", inversedBy="enfants")
     * @ORM\JoinColumn(name="parent_id",referencedColumnName="id", nullable=true)
     */
    private $parent;

    /**
     * @ORM\OneToMany(targetEntity="AppBundle\Entity\User", mappedBy="parent")
     */
    private $enfants;

    /**
     * @ORM\ManyToOne(targetEntity="ScolariteBundle\Entity\Classe", inversedBy="eleves")
     * @ORM\JoinColumn(name="classeeleve_id",referencedColumnName="id", nullable=true)
     */
    private $classedeseleves;

    /**
     * @ORM\ManyToOne(targetEntity="ScolariteBundle\Entity\Classe", inversedBy="enseignants")
     * @ORM\JoinColumn(name="classeenseignant_id",referencedColumnName="id", nullable=true)
     */
    private $classe;

    /**
     * @return mixed
     */
    public function getClasse()
    {
        return $this->classe;
    }

    /**
     * @param mixed $classe
     */
    public function setClasse($classe)
    {
        $this->classe = $classe;
    }

    /**
     * @return mixed
     */
    public function getClassedeseleves()
    {
        return $this->classedeseleves;
    }

    /**
     * @param mixed $classedeseleves
     */
    public function setClassedeseleves($classedeseleves)
    {
        $this->classedeseleves = $classedeseleves;
    }

    /**
     * @return mixed
     */
    public function getParent()
    {
        return $this->parent;
    }

    /**
     * @param mixed $parent
     */
    public function setParent($parent)
    {
        $this->parent = $parent;
    }

    /**
     * @return string
     */
    public function getNom()
    {
        return $this->nom;
    }

    /**
     * @param string $nom
     */
    public function setNom($nom)
    {
        $this->nom = $nom;
    }

    /**
     * @return string
     */
    public function getPrenom()
    {
        return $this->prenom;
    }

    /**
     * @param string $prenom
     */
    public function setPrenom($prenom)
    {
        $this->prenom = $prenom;
    }

    /**
     * @var string
     *
     * @ORM\Column(name="prenom", type="string", length=255)
     */

    private $prenom;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="date_embauche", type="date", nullable=true)
     */

    private $date_embauche;

    /**
     * @return \DateTime
     */
    public function getDateEmbauche()
    {
        return $this->date_embauche;
    }

    /**
     * @param \DateTime $date_embauche
     */
    public function setDateEmbauche($date_embauche)
    {
        $this->date_embauche = $date_embauche;
    }

    /**
     * @return \DateTime
     */
    public function getDateInscription()
    {
        return $this->date_inscription;
    }

    /**
     * @param \DateTime $date_inscription
     */
    public function setDateInscription($date_inscription)
    {
        $this->date_inscription = $date_inscription;
    }

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="date_inscription", type="date", nullable=true)
     */

    private $date_inscription;

    /**
     * @ORM\OneToMany(targetEntity="EnseignantBundle\Entity\Notes", mappedBy="enseignant")
     */
    private $notes;

    /**
     * @ORM\OneToMany(targetEntity="EnseignantBundle\Entity\Notes", mappedBy="eleve")
     */
    private $noteseleve;

    /**
     * @ORM\OneToMany(targetEntity="EnseignantBundle\Entity\Absences", mappedBy="enseignant")
     */
    private $absences;

    /**
     * @ORM\OneToMany(targetEntity="EnseignantBundle\Entity\Absences", mappedBy="eleve")
     */
    private $absenceseleve;

    /**
     * @ORM\OneToMany(targetEntity="EnseignantBundle\Entity\Sanctions", mappedBy="enseignant")
     */
    private $sanctions;

    /**
     * @ORM\OneToMany(targetEntity="EnseignantBundle\Entity\Sanctions", mappedBy="eleve")
     */
    private $sanctionseleve;

    /**
     * @ORM\OneToMany(targetEntity="EnseignantBundle\Entity\Moyennes", mappedBy="eleve")
     */
    private $moyennes;

    /**
     * @return ArrayCollection
     */
    public function getMoyennes()
    {
        return $this->moyennes;
    }

    /**
     * @param ArrayCollection $moyennes
     */
    public function setMoyennes($moyennes)
    {
        $this->moyennes = $moyennes;
    }

    /**
     * @return ArrayCollection
     */
    public function getSanctions()
    {
        return $this->sanctions;
    }

    /**
     * @param ArrayCollection $sanctions
     */
    public function setSanctions($sanctions)
    {
        $this->sanctions = $sanctions;
    }

    /**
     * @return ArrayCollection
     */
    public function getSanctionseleve()
    {
        return $this->sanctionseleve;
    }

    /**
     * @param ArrayCollection $sanctionseleve
     */
    public function setSanctionseleve($sanctionseleve)
    {
        $this->sanctionseleve = $sanctionseleve;
    }

    /**
     * @return ArrayCollection
     */
    public function getNotes()
    {
        return $this->notes;
    }

    /**
     * @param ArrayCollection $notes
     */
    public function setNotes($notes)
    {
        $this->notes = $notes;
    }

    /**
     * @return ArrayCollection
     */
    public function getNoteseleve()
    {
        return $this->noteseleve;
    }

    /**
     * @param ArrayCollection $noteseleve
     */
    public function setNoteseleve($noteseleve)
    {
        $this->noteseleve = $noteseleve;
    }

    /**
     * @return ArrayCollection
     */
    public function getAbsences()
    {
        return $this->absences;
    }

    /**
     * @param ArrayCollection $absences
     */
    public function setAbsences($absences)
    {
        $this->absences = $absences;
    }

    /**
     * @return ArrayCollection
     */
    public function getEnfants()
    {
        return $this->enfants;
    }

    /**
     * @param ArrayCollection $enfants
     */
    public function setEnfants($enfants)
    {
        $this->absences = $enfants;
    }

    /**
     * @return ArrayCollection
     */
    public function getAbsenceseleve()
    {
        return $this->absenceseleve;
    }

    /**
     * @param ArrayCollection $absenceseleve
     */
    public function setAbsenceseleve($absenceseleve)
    {
        $this->absenceseleve = $absenceseleve;
    }

    public function __construct()
    {
        parent::__construct();
        $this->notes = new ArrayCollection();
        $this->noteseleve = new ArrayCollection();
        $this->absences = new ArrayCollection();
        $this->absenceseleve = new ArrayCollection();
        $this->sanctions = new ArrayCollection();
        $this->sanctionseleve = new ArrayCollection();
        $this->moyennes = new ArrayCollection();
        $this->enfants=new ArrayCollection();
    }
}

