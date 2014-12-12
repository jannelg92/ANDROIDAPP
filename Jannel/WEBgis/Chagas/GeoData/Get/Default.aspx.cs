using System;
using System.Collections.Generic;

using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data.SqlClient;
using Microsoft.SqlServer.Types;
using System.Data.SqlTypes;

public partial class Default : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {

        try
        {



            SqlConnection connection = new SqlConnection("user id=jannel.gonzales;" +
                                            "password=two013loveKILLS;" +
                                            "server=GEOG489-03.tamu.edu;" +
                                            "database=Lab6; " +
                                            "connection timeout=30");


            string sql = "SELECT * ";
           
            sql += " from  ";
            sql += " countries_borders ";
            

            SqlCommand command = new SqlCommand(sql, connection);

            

            command.Connection.Open();
            SqlDataReader dataReader = command.ExecuteReader();

            Response.Write("{ ");
            Response.Write(Environment.NewLine);
            Response.Write("\"items\": ");
            Response.Write(Environment.NewLine);
            Response.Write("[");
            Response.Write(Environment.NewLine);

            int i = 0;

            while (dataReader.Read())
            {

                if (i > 0)
                {
                    Response.Write(",");
                }

                // need to add reference to c:\Program Files (x86)\Microsoft SQL Server\100\SDK\Assemblies\Microsoft.SqlServer.Types.dll

                SqlGeometry geom = (SqlGeometry)dataReader["geom"];

                //geom = geom.STBuffer(10);

                char[] chars = geom.STAsText().Value;
                string wkt = new string(chars);

                Response.Write("\t");
                Response.Write(" { ");
                Response.Write(Environment.NewLine);
                Response.Write("\t");
                Response.Write("\"name\": ");
                Response.Write("\"" + dataReader["name"] + "\", ");
                Response.Write(Environment.NewLine);
                Response.Write("\t");
                Response.Write("\"pop\": ");
                Response.Write("\"" + dataReader["pop"] + "\", ");
                Response.Write(Environment.NewLine);
                Response.Write("\t");
                Response.Write("\"wkt\": ");
                Response.Write("\"" + wkt  + "\" ");
                Response.Write(Environment.NewLine);
                Response.Write(" } ");
                Response.Write(Environment.NewLine);

                i++;
            }

            Response.Write("]");
            Response.Write("} ");

        }
        catch (Exception ex)
        {
            Response.Write("Exception ocurred: " + ex.Message);
        }
    }
}