<%@ Page Language="C#" AutoEventWireup="true" CodeFile="m2_PolygonFromFile.aspx.cs" Inherits="m2_ShowGMLPolygonFromFile" %>

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

<!-- This file will be wherever you put the folder locally on your server. The resolveURL part says to find it from the ~ of the site (the root) -->
<script type="text/javascript"  src="http://geog489-03.tamu.edu/Data/Geog489Lab6/scripts/csvParser.js"></script>

<script type="text/javascript" >

    var map;
    var features = new Array();

    // update this to be the correct path in your site
    var csvLocation = "http://geog489-03.tamu.edu/Data/Geog489Lab6/data/Countries/countries_bordersNorthAmerica.csv"

    $(document).ready(function () {
        $("button").click(function () {

            MapWKT();

        });
    });


function handleDone(data, textStatus)
{

    // Create a new Wicket instance
    var wkt = new Wkt.Wkt();

    var array = $.getCSVParser()(data);
    if (array)
    {
        for (var j = 1; j < array.length; j++)
        {

            var wktVal = array[j][0];
            wkt.read(wktVal);

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
    }
}


function MapWKT()
{

    $.get(csvLocation, handleDone);
    
}


		
</script>



 

 
 <script type="text/javascript" >
     
     function initialize() {
         var mapOptions = {
             zoom: 3,
             center: new google.maps.LatLng(31.19055265, -101.46077002)
         };

         map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
     }

     google.maps.event.addDomListener(window, 'load', initialize);
 </script>
 

</head>
<body>


<div id="map-canvas"></div>



<button>Map North America</button>


</body>
</html>