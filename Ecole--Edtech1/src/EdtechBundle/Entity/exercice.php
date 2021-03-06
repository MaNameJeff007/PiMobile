<?php

namespace EdtechBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * exercice
 *
 * @ORM\Table(name="exercice")
 * @ORM\Entity(repositoryClass="EdtechBundle\Repository\exerciceRepository")
 */
class exercice
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
     * @var string
     *
     * @ORM\Column(name="question", type="string", length=255)
     */
    private $question;

    /**
     * @var string
     *
     * @ORM\Column(name="reponse", type="string", length=255)
     */
    private $reponse;

    /**
     * @var string
     *
     * @ORM\Column(name="score", type="string", length=255)
     */
    private $score;


    /**
     * @ORM\ManyToOne(targetEntity="course")
     * @ORM\JoinColumn(name="course_id", referencedColumnName="id")
     */
    private $course;

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
     * Set question
     *
     * @param string $question
     *
     * @return exercice
     */
    public function setQuestion($question)
    {
        $this->question = $question;

        return $this;
    }

    /**
     * Get question
     *
     * @return string
     */
    public function getQuestion()
    {
        return $this->question;
    }

    /**
     * Set reponse
     *
     * @param string $reponse
     *
     * @return exercice
     */
    public function setReponse($reponse)
    {
        $this->reponse = $reponse;

        return $this;
    }

    /**
     * Get reponse
     *
     * @return string
     */
    public function getReponse()
    {
        return $this->reponse;
    }

    /**
     * Set score
     *
     * @param string $score
     *
     * @return exercice
     */
    public function setScore($score)
    {
        $this->score = $score;

        return $this;
    }

    /**
     * Get score
     *
     * @return string
     */
    public function getScore()
    {
        return $this->score;
    }

    /**
     * Set course
     *
     * @param \EdtechBundle\Entity\course $course
     *
     * @return exercice
     */
    public function setCourse(\EdtechBundle\Entity\course $course = null)
    {
        $this->course = $course;

        return $this;
    }

    /**
     * Get course
     *
     * @return \EdtechBundle\Entity\course
     */
    public function getCourse()
    {
        return $this->course;
    }
}
