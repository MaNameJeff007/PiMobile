<?php

namespace EnseignantBundle\Entity;
use Symfony\Component\Validator\Constraints as Assert;

use Doctrine\ORM\Mapping as ORM;

/**
 * Absences
 *
 * @ORM\Table(name="absences")
 * @ORM\Entity(repositoryClass="EnseignantBundle\Repository\AbsencesRepository")
 */
class Absences
{
    /**
     * @var int
     *
     * @ORM\Column(name="id", type="integer")
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="AUTO")
     */
    private $id;


    /**
     * @var \DateTime
     *
     * @ORM\Column(name="dateabs", type="date", nullable=true)
     * @Assert\NotBlank
     */

    private $date_abs;

    /**
     * @var string
     *
     * @ORM\Column(name="justification", type="string", length=255, nullable=true)
     */
    private $justification;


    /**
     * @var int
     *
     * @ORM\Column(name="heuredebut", type="integer")
     * @Assert\Range(min="8", max="16", minMessage="les cours ne commencent pas avant 8 heure", maxMessage="les cours finissent a 16h")
     */
    private $heuredebut;

    /**
     * @ORM\ManyToOne(targetEntity="AppBundle\Entity\User", inversedBy="absences")
     * @ORM\JoinColumn(name="enseignant_id",referencedColumnName="id")
     */
    private $enseignant;


    /**
     * @ORM\ManyToOne(targetEntity="AppBundle\Entity\User", inversedBy="absenceseleve")
     * @ORM\JoinColumn(name="eleve_id",referencedColumnName="id")
     * @Assert\NotBlank
     */
    private $eleve;

    /**
     * @return mixed
     */
    public function getEnseignant()
    {
        return $this->enseignant;
    }

    /**
     * @param mixed $enseignant
     */
    public function setEnseignant($enseignant)
    {
        $this->enseignant = $enseignant;
    }

    /**
     * @return mixed
     */
    public function getEleve()
    {
        return $this->eleve;
    }

    /**
     * @param mixed $eleve
     */
    public function setEleve($eleve)
    {
        $this->eleve = $eleve;
    }

    /**
     * @return \DateTime
     */
    public function getDateAbs()
    {
        return $this->date_abs;
    }

    /**
     * @param \DateTime $date_abs
     */
    public function setDateAbs($date_abs)
    {
        $this->date_abs = $date_abs;
    }

    /**
     * @return string
     */
    public function getJustification()
    {
        return $this->justification;
    }

    /**
     * @param string $raison
     */
    public function setJustification($justification)
    {
        $this->justification = $justification;
    }

    /**
     * @return int
     */
    public function getHeuredebut()
    {
        return $this->heuredebut;
    }

    /**
     * @param int $heuredebut
     */
    public function setHeuredebut($heuredebut)
    {
        $this->heuredebut = $heuredebut;
    }

    /**
     * @return int
     */
    public function getHeurefin()
    {
        return $this->heurefin;
    }

    /**
     * @param int $heurefin
     */
    public function setHeurefin($heurefin)
    {
        $this->heurefin = $heurefin;
    }

    /**
     * @return bool
     */
    public function isEtat()
    {
        return $this->etat;
    }

    /**
     * @param bool $etat
     */
    public function setEtat($etat)
    {
        $this->etat = $etat;
    }

    /**
     * @var int
     *
     * @ORM\Column(name="heurefin", type="integer")
     * @Assert\Range(min="8", max="16", minMessage="les cours ne commencent pas avant 8 heure", maxMessage="les cours finissent a 16h")
     */
    private $heurefin;

    /**
     * @var boolean
     *
     * @ORM\Column(name="etat", type="boolean")
     */
    private $etat;

    /**
     * Get id
     *
     * @return int
     */
    public function getId()
    {
        return $this->id;
    }
}

