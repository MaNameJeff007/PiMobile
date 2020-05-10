$("document").ready(function () {

    $("#searchPermutation").keyup(function () {
        $.ajax({
            type: 'get',
            url: 'http://127.0.0.1:8000/searchpermutationajax/' + $(this).val(),
            beforeSend: function () {
                console.log('ca change');//+$(this).val());
            },
            success: function (data) {

                $("tr").remove();
                var xx=
                    "<tr class='tr-shadow'>" +
                    "<th>ID</th> " +
                    "<th>Classe souhaitée</th>" +
                    "<th>Raison de la demande</th>" +
                    "<th>Etat</th>" +
                    "</tr>";
                console.log("1");
                $("#resultSearchPermutation").append($(xx));
                console.log("2");
                $.each(data.valeur, function (index, value) {

                    var x="<tr class='tr-shadow'>" +
                                "<td>" + value.id+"</td>" +
                                "<td>" + value.classeS+"</td>" +
                                "<td>" + value.raison+"</td>" +
                                 "<td class='status--denied'>" + value.etat+"</td>" +
                         "</tr>";
                    $("#resultSearchPermutation").append($(x));
                });
          }
        });

    });

});
