<?php

namespace EdtechBundle\Form;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\FileType;

use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Symfony\Component\HttpFoundation\File\UploadedFile;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Validator\Constraints\File;
use  Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Form\Extension\Core\Type\IntegerType;

class courseType extends AbstractType
{
    /**
     * {@inheritdoc}
     */
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder->add('nom')->add('description')->add('contenu' , FileType::class,[
            'constraints' => [
        new File([
            'maxSize' => '4096k',
            'mimeTypes' => [
                'application/pdf',
                'application/x-pdf',
            ],
            'mimeTypesMessage' => 'Please upload a valid PDF document',


        ])
    ],
           'data_class' => null ])->add('typeIntelligence', IntegerType::class, array(

                'constraints' => array(
                    new Assert\NotBlank(),
                    new Assert\Type('integer'),
                    new Assert\Regex(array(
                            'pattern' => '/^[0-9]\d*$/',
                            'message' => 'Please use only positive numbers.'
                        )
                    ),
                    new Assert\Length(array('max' => 4))
                )
            )
            )->add('niveau', IntegerType::class, array(

            'constraints' => array(
                new Assert\NotBlank(),
                new Assert\Type('integer'),
                new Assert\Regex(array(
                        'pattern' => '/^[0-9]\d*$/',
                        'message' => 'Please use only positive numbers.'
                    )
                ),
                new Assert\Length(array('max' => 7))
            )
        ));
    }/**
     * {@inheritdoc}
     */
    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults(array(
            'data_class' => 'EdtechBundle\Entity\course'
        ));
    }

    /**
     * {@inheritdoc}
     */
    public function getBlockPrefix()
    {
        return 'edtechbundle_course';
    }


}
