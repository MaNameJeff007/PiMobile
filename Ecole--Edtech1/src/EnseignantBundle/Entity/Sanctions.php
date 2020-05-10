<?php

namespace EnseignantBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Sanctions
 *
 * @ORM\Table(name="sanctions")
 * @ORM\Entity(repositoryClass="EnseignantBundle\Repository\SanctionsRepository")
 */
class Sanctions
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
     * @ORM\Column(name="date_sanction", type="date", nullable=true)
     */

    private $date_sanction;

    /**
     * @var string
     *
     * @ORM\Column(name="raisonsanction", type="string", length=255, nullable=false)
     */
    private $raisonsanction;

    /**
     * @var string
     *
     * @ORM\Column(name="punition", type="string", length=255, nullable=false)
     */
    private $punition;

    /**
     * @return string
     */
    public function getPunition()
    {
        return $this->punition;
    }

    /**
     * @param string $punition
     */
    public function setPunition($punition)
    {
        $this->punition = $punition;
    }

    /**
     * @return \DateTime
     */
    public function getDateSanction()
    {
        return $this->date_sanction;
    }

    /**
     * @param \DateTime $date_sanction
     */
    public function setDateSanction($date_sanction)
    {
        $this->date_sanction = $date_sanction;
    }

    /**
     * @ORM\ManyToOne(targetEntity="AppBundle\Entity\User", inversedBy="sanctions")
     * @ORM\JoinColumn(name="enseignant_id",referencedColumnName="id")
     */
    private $enseignant;


    /**
     * @ORM\ManyToOne(targetEntity="AppBundle\Entity\User", inversedBy="sanctionseleve")
     * @ORM\JoinColumn(name="eleve_id",referencedColumnName="id")
     */
    private $eleve;

    /**
     * @var boolean
     *
     * @ORM\Column(name="etat", type="boolean")
     */
    private $etat;

    /**
     * @return string
     */
    public function getRaisonsanction()
    {
        return $this->raisonsanction;
    }

    /**
     * @param string $raisonsanction
     */
    public function setRaisonsanction($raisonsanction)
    {
        $this->raisonsanction = $raisonsanction;
    }

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
     * Get id
     *
     * @return int
     */
    public function getId()
    {
        return $this->id;
    }
}

