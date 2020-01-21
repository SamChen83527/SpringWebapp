/**
 * 
 */

function createServerConnection(serverDomain){
		var serverConnection = $TA.Service(serverDomain);
		
		return serverConnection;
}

function getLatestObservation(serverConnection, datastream_id){
	// service/Datastreams(1)/Observations?$orderby=phenomenonTime desc&$top=1
	let getDataModel = $TA.Model('Datastream',{id:datastream_id}).Model('Observation').$orderby('phenomenonTime','desc').$top(1);	
	let resultSet = serverConnection.getFormatData(getDataModel);	
	let latestObservation = resultSet.next();
	
	return latestObservation;
}