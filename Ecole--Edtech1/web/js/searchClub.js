$("document").ready(function () {

    $("#searchClub").keyup(function () {
        $.ajax({
            type: 'get',
            url: 'http://127.0.0.1:8000/searchclubajax/' + $(this).val(),
            beforeSend: function () {
                console.log('ca change');//+$(this).val());
            },
            success: function (data) {

                $("tr").remove();
                var xx= "<tr class='row'>" +
                    "<th>Nom du club</th> " +
                    "<th>Responsable du club</th>" +
                    "<th >Club </th>" +
                    "</tr>";

                $("#resultSearchClub").append($(xx));

                $.each(data.valeur, function (index, value) {

                    var x="<tr class='row'>" +
                                "<td class='main-content'>" + value.nomClub+"</td>" +
                                "<td class='main-content'>" + value.nomUser+"</td>" +
                                "<td>  <img src='../images/"+value.nomImage+" ' style='width: 50px;height: 50px'/></td>" +
                         "</tr>";
                    $("#resultSearchClub").append($(x));
                });
          }
        });

    });

});
