/* SOCIAL NETWORK css edited by Andriy Khrobak 0631461900 */
function send()
{

var data = $('#mydata').val()
       $.ajax({
                type: "GET",
                
                url: "/sn/admin/createInvite",
                data: "data="+data,
                success: function(html) {
                       $("#result").empty();
                       $("#result").append(html.invite);
                }
               
        });
 document.getElementById('genbtn').style.display = 'none'; 
}