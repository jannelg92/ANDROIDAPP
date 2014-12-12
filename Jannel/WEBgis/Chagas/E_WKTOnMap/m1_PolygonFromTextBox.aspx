<%@ Page Language="C#" AutoEventWireup="true" CodeFile="m1_PolygonFromTextBox.aspx.cs" Inherits="m1_ShowGMLPolygon" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>

<style>
      html, body, #map-canvas {
        height: 500px;
		width: 500px;
        margin: 0px;
        padding: 0px
      }
    </style>
	
	
<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
<script type="text/javascript"  src="http://geojam.net/static/wicket/wicket.src.js" ></script>
<script type="text/javascript"  src="http://geojam.net/static/wicket/wicket-gmap3.src.js" ></script>
<script type="text/javascript"  src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>

<script>

    var map;
    var features = new Array();

    $(document).ready(function () {
        $("button").click(function () {

            MapWKT();

        });
    });




    function MapWKT()
{
    // Create a new Wicket instance
    var wkt = new Wkt.Wkt();

    var gml = $('#wkt').val();


    wkt.read(gml);


    var obj = wkt.toObject(map.defaults); // Make an object

    if (Wkt.isArray(obj))
    {
        for (i in obj)
        {
            if (obj.hasOwnProperty(i) && !Wkt.isArray(obj[i]))
            {
                obj[i].setMap(map);
                features.push(obj[i]);
            }
        }
    } 
    else
    {
        obj.setMap(map); // Add it to the map
        features.push(obj);
    }

    
}
		
</script>



 

 
 <script>
     
     function initialize() {
         var mapOptions = {
             zoom: 8,
             center: new google.maps.LatLng(31.19055265, -101.46077002)
         };

         map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
     }

     google.maps.event.addDomListener(window, 'load', initialize);
 </script>
 

</head>
<body>


<div id="map-canvas"></div>


<textarea type="text" name="wkt" id="wkt" style="width:400px; height:150px">
MULTIPOLYGON (((40 40, 20 45, 45 30, 40 40)), ((20 35, 45 20, 30 5, 10 10, 10 30, 20 35), (30 20, 20 25, 20 15, 30 20)))
</textarea>
<br />
<button>Map It</button>


</body>
</html>