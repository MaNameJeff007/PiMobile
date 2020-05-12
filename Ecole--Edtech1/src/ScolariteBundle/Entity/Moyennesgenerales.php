<?php

namespace ScolariteBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Moyennesgenerales
 *
 * @ORM\Table(name="moyennesgenerales")
 * @ORM\Entity(repositoryClass="ScolariteBundle\Repository\MoyennesgeneralesRepository")
 */
class Moyennesgenerales
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
     * @var int
     *
     * @ORM\Column(name="eleve", type="integer")
     */
    private $eleve;

    /**
     * @var float
     *
     * @ORM\Column(name="moyG", type="float")
     */
    private $moyG;

    /**
     * @var int
     *
     * @ORM\Column(name="classe", type="integer")
     */
    private $classe;


    /**
     * Get id
     *
     * @return int
     */
    public function getId()
    {
        return $this->id;
    }

    /**
     * Set eleve
     *
     * @param integer $eleve
     *
     * @return Moyennesgenerales
     */
    public function setEleve($eleve)
    {
        $this->eleve = $eleve;

        return $this;
    }

    /**
     * Get eleve
     *
     * @return int
     */
    public function getEleve()
    {
        return $this->eleve;
    }

    /**
     * Set moyG
     *
     * @param float $moyG
     *
     * @return Moyennesgenerales
     */
    public function setMoyG($moyG)
    {
        $this->moyG = $moyG;

        return $this;
    }

    /**
     * Get moyG
     *
     * @return float
     */
    public function getMoyG()
    {
        return $this->moyG;
    }

    /**
     * Set classe
     *
     * @param integer $classe
     *
     * @return Moyennesgenerales
     */
    public function setClasse($classe)
    {
        $this->classe = $classe;

        return $this;
    }

    /**
     * Get classe
     *
     * @return int
     */
    public function getClasse()
    {
        return $this->classe;
    }
}

