/* jshint esversion: 6 */
//$TA
;(function () {
	class $TA {
		constructor() {
			class Model {
				constructor(method) {
					let modelSet = Object.assign({
						Thing: function (id, name, description, properties, Datastream = [], Location = [], HistoricalLocation = []) {
							this.type = 'Thing';
							this.id = id;
							this.name = name;
							this.description = description;
							this.properties = properties;
							this.Datastream = Datastream; //此開始皆為有關聯之CLASS
							this.Location = Location;
							this.HistoricalLocation = HistoricalLocation;
							return this;
						},
						Location: function (id, name, description, encodingType = "application/vnd.geo+json", location, Thing = [], HistoricalLocation = []) {
							this.type = 'Location';
							this.id = id;
							this.name = name;
							this.description = description;
							this.encodingType = encodingType;
							this.location = location;
							this.Thing = Thing; //此開始皆為有關聯之CLASS
							this.HistoricalLocation = HistoricalLocation;
							return this;
						},
						HistoricalLocation: function (id, time, Thing, Location = []) {
							this.type = 'HistoricalLocation';
							this.id = id;
							this.time = time;
							this.Thing = Thing; //此開始皆為有關聯之CLASS
							this.Location = Location;
							return this;
						},
						Datastream: function (id, name, description, observationType, unitOfMeasurement, observedArea, phenomenonTime, Thing, Sensor, ObservedProperty = [], Observation = []) {
							this.type = 'Datastream';
							this.id = id;
							this.name = name;
							this.description = description;
							this.observationType = observationType;
							this.unitOfMeasurement = unitOfMeasurement;
							this.observedArea = observedArea;
							this.phenomenonTime = phenomenonTime;
							this.Thing = Thing; //此開始皆為有關聯之CLASS
							this.Sensor = Sensor;
							this.ObservedProperty = ObservedProperty;
							this.Observation = Observation;
							//由api回傳的response中未有resultTime 
							return this;
						},
						Sensor: function (id, name, description, encodingType, metadata, Datastream = []) {
							this.type = 'Sensor';
							this.id = id;
							this.name = name;
							this.description = description;
							this.encodingType = encodingType;
							this.metadata = metadata;
							this.Datastream = Datastream; //此開始皆為有關聯之CLASS 
							return this;
						},
						ObservedProperty: function (id, name, definition, description, Datastream = []) {
							this.type = 'ObservedProperty';
							this.id = id;
							this.name = name;
							this.definition = definition;
							this.description = description;
							this.Datastream = Datastream; //此開始皆為有關聯之CLASS 
							return this;
						},
						Observation: function (id, phenomenonTime, resultTime, result, Datastream, FeatureOfInterest) {
							this.type = 'Observation';
							this.id = id;
							this.phenomenonTime = phenomenonTime;
							this.resultTime = resultTime;
							this.result = result;
							this.Datastream = Datastream; //此開始皆為有關聯之CLASS
							this.FeatureOfInterest = FeatureOfInterest;
							return this;
						},
						FeatureOfInterest: function (id, name, description, encodingType, feature, Observation = []) {
							this.type = 'FeatureOfInterest';
							this.id = id;
							this.name = name;
							this.description = description;
							this.encodingType = encodingType;
							this.feature = feature;
							this.Observation = Observation; //此開始皆為有關聯之CLASS
							return this;
						}
					}, extendObjModel);

					let newModel = (method, setting = undefined) => {
						return new Model(method, setting);
					};
					let getModelByUrl = (url, preModel = false) => {
						return getModel(url, preModel);
					};
					this.fn = this.prototype = {
						modelObject: function (method) {
							let a = Array.prototype.slice.call(arguments)[0];
							if (modelSet[a[0]] != undefined) {
								let R = {};
								if (typeof method === 'object' && !Array.isArray(method[1])) {
									R = Object.assign(modelSet[a[0]].apply(this, a[1]), method[1]);
								} else {
									R = modelSet[a[0]].apply(this, a[1]);
								}
								R.__proto__.__setting__ = {
									subject: (R.id != undefined) ? a[0] + 's(' + R.id + ')' : a[0] + 's',
									predicate: [],
									modelRecord:{},
									history: [a[0]],
									parent: undefined,
									child: undefined,
									type: a[0],
									uninserted: Object.keys(this).filter(i => {
										return this[i] === undefined || this[i].length === 0;
									})
								};
								if (R.id) {
									R.__setting__.modelRecord[a[0]] = {};
									R.__setting__.modelRecord[a[0]][R.id] = this;
								}
								R.__proto__.type = a[0];
								return R;
							} else {
								switch (a[0]) {
									case "__status__":
										return {
											modelObject: Object.keys(modelSet),
											modelFunction: Object.keys(this.__proto__)
										};
									default:
										try {
											return getModelByUrl(a[0]);
										}
										catch (err) {
											throw new Error(`The '${a[0]}' model does not exist.`);
										}
								}
							}
						},
						getJSONStringify: function () {
							return cleanStringify(this);
						},
						unInserted: function () {
							return this.__setting__.uninserted = Object.keys(this).filter(i => {
								return this[i] === undefined || this[i].length === 0;
							});
						},
						filter: function (modelType, filterObj = false) {
							if (typeof modelType === "string" && this.__setting__.modelRecord[modelType] != undefined) {
								if (typeof filterObj === "function") {
									return Object.values(this.__setting__.modelRecord[modelType]).filter(model => filterObj(model));
								} else {
									let keys = Object.keys(filterObj);
									let L = keys.length;
									return Object.values(this.__setting__.modelRecord[modelType]).filter(model => {
										return keys.filter(key => {
											return typeof model[key] !== "undefined" && model[key] == filterObj[key];
										}).length === L;
									});
								}
							} else if (typeof modelType === "object" && filterObj === false) {
								let keys = Object.keys(modelType);
								let L = keys.length;
								return Object.values(this.__setting__.modelRecord).map(Type => {
									return Object.values(Type).filter(model => {
										return keys.filter(key => {
											return typeof model[key] !== "undefined" && model[key] == modelType[key];
										}).length === L;
									});
								}).reduce((a, b) => {
									return a.concat(b);
								});
							} else if (typeof modelType === "function") {
								return Object.values(this.__setting__.modelRecord).map(Type => {
									return Object.values(Type).filter(model => modelType(model));
								}).reduce((a, b) => {
									return a.concat(b);
								});
							} else {
								return [];
							}
						},
						cd: function (modelType, id) {
							let tp = this.__setting__.modelRecord[modelType];
							return (tp != undefined) ? ((tp[id]) ? tp[id] : this) : this;
						},
						attr: function (attr, value = false) {
							let keys = Object.keys(this);
							let isModel = Object.keys(modelSet).includes(attr);
							if (typeof attr === "object") {
								Object.keys(attr).map(i => this.attr(i, attr[i]));
								return this;
							} else if (typeof attr !== "string" || !keys.includes(attr)) {
								return (attr == undefined) ? keys : undefined;
							} else if (value != false) {
								if (attr == "id") {
									if (this.id != undefined) {
										delete this.__setting__.modelRecord[this.type][this.id];
										this.__setting__.modelRecord[this.type][value] = this;
									} else {
										if (this.__setting__.modelRecord[this.type] == undefined) {
											this.__setting__.modelRecord[this.type] = {};
										}
										this.__setting__.modelRecord[this.type][value] = this;
									}
								}
								if (isModel) {
									throw new Error(`You can't set the '${attr}' Model directly by .attr() . Please use .Model('${attr}') or .atModel('${attr}') .`);
								} else {
									this[attr] = value;
								}
								return this;
							} else {
								return this[attr];
							}
						},
						_insertModel: function (Model) {
							if (modelSet[Model.type] && this[Model.type]) {
								if (Array.isArray(this[Model.type])) {
									this[Model.type].push(Model);
								} else {
									this[Model.type] = Model;
								}
								return this;
							} else {
								throw new Error(`You can't insert illegal data.`);
							}
						},
						insertModel: function (...Models) {
							Models.map(Model => {
								if (Object.keys(this).includes(Model.type)) {
									if (Array.isArray(this[Model.type])) {
										if (Array.isArray(Model[this.type])) {
											this[Model.type] = this[Model.type].concat(Model);
											Model[this.type] = Model[this.type].concat(this);
										} else {
											Model[this.type] = this;
											this[Model.type] = this[Model.type].concat(Model);
										}
									} else {
										this[Model.type] = Model;
										Model[this.type] = Model[this.type].concat(this);
									}
									Object.keys(this.__setting__.modelRecord).concat(Object.keys(Model.__setting__.modelRecord)).map(key => {
										this.__setting__.modelRecord[key] = Object.assign(this.__setting__.modelRecord[key] || {}, Model.__setting__.modelRecord[key] || {});
									});
									Model.__setting__.modelRecord = this.__setting__.modelRecord;
									Model.__setting__.parent = this;
								} else {
									throw new Error(`The '${Model.type}' model is not an attributes of '${this.type}'.`);
								}
							});
							return this;
						},
						exModel: function (...URLs) {
							URLs.map(URL => {
								getModelByUrl(URL, this);
							});
							return this;
						},
						atModel: function (target) {
							if (Object.keys(this).includes(target)) {
								let setting = Array.prototype.slice.call(arguments, 1)[0];
								let F = (this.__setting__.modelRecord[target] != undefined && setting != undefined && setting.id != undefined) ? this.__setting__.modelRecord[target][setting.id] : undefined;
								let G = (F == undefined) ? newModel(target, setting) : F;
								let T = '';
								if (Array.isArray(G[this.type])) {
									if (G[this.type].find(i => i.id != undefined && i.id === this.id) == undefined) {
										G[this.type].push(this);
									}
									T = G.type;
								} else {
									G[this.type] = this;
									T = (G.id != undefined) ? G.type + 's(' + G.id + ')' : G.type + 's';
								}
								G.__setting__.subject = this.__setting__.subject + '/' + T;
								G.__setting__.parent = this;
								this.__setting__.history.push(G.type);
								G.__setting__.history = this.__setting__.history;

								if (G.id != undefined && this.__setting__.modelRecord[G.type] == undefined) {
									G.__setting__.modelRecord = Object.assign(this.__setting__.modelRecord, G.__setting__.modelRecord);
								} else if (G.id != undefined) {
									this.__setting__.modelRecord[G.type][G.id] = G;
									G.__setting__.modelRecord = this.__setting__.modelRecord;
								} else {
									G.__setting__.modelRecord = this.__setting__.modelRecord;
								}

								if (Array.isArray(this[target])) {
									if(F == undefined){this[target].push(G);}
									this.__setting__.child = this[target][this[target].length - 1];
								} else {
									if (this[target] == undefined) {
										this[target] = G;
									} else {
										this[target].__setting__.subject = this.__setting__.subject + '/' + T;
										this[target].__setting__.history = this.__setting__.history;
									}
									this.__setting__.child = this[target];
								}
								return this;
							} else {
								throw new Error(`The '${target}' model is not an attributes of '${this.type}'.`);
							}
						},
						Model: function (target) {
							if (Object.keys(this).includes(target)) {
								let setting = Array.prototype.slice.call(arguments, 1)[0];
								let F = (this.__setting__.modelRecord[target] != undefined && setting != undefined && setting.id != undefined) ? this.__setting__.modelRecord[target][setting.id] : undefined;
								let G = (F == undefined) ? newModel(target, setting) : F;
								let T = '';
								if (Array.isArray(G[this.type])) {
									if (G[this.type].find(i => i.id != undefined && i.id === this.id) == undefined) {
										G[this.type].push(this);
									}
									T = G.type;
								} else {
									G[this.type] = this;
									T = (G.id != undefined) ? G.type + 's(' + G.id + ')' : G.type + 's';
								}
								G.__setting__.subject = this.__setting__.subject + '/' + T;
								G.__setting__.parent = this;
								this.__setting__.history.push(G.type);
								G.__setting__.history = this.__setting__.history;

								if (G.id != undefined && this.__setting__.modelRecord[G.type] == undefined) {
									G.__setting__.modelRecord = Object.assign(this.__setting__.modelRecord, G.__setting__.modelRecord);
								} else if (G.id != undefined) {
									this.__setting__.modelRecord[G.type][G.id] = G;
									G.__setting__.modelRecord = this.__setting__.modelRecord;
								} else {
									G.__setting__.modelRecord = this.__setting__.modelRecord;
								}

								if (Array.isArray(this[target])) {
									if (F == undefined) {
										this[target].push(G);
									}
									return this.__setting__.child = this[target][this[target].length - 1];
								} else {
									if (this[target] == undefined) {
										this[target] = G;
									} else {
										this[target].__setting__.subject = this.__setting__.subject + '/' + T;
										this[target].__setting__.history = this.__setting__.history;
									}
									return this.__setting__.child = this[target];
								}
							} else {
								try {
									return getModelByUrl(target, this);
								} catch (err) {
									throw new Error(`The '${target}' model is not an attributes of '${this.type}'.`);
								}
							}
						},
						$expand: function (...Models) {
							this.__setting__.predicate.push('$expand=' + Models.map(Model => {
								let getU = (url, model) => {
									if (model.__setting__.parent != undefined) {
										url = '/' + ((Array.isArray(model.__setting__.parent[model.type])) ? model.type + 's' : model.type) + url;
										return getU(url, model.__setting__.parent);
									} else {
										return url = ((Array.isArray(this[model.type])) ? model.type + 's' : model.type) + url;
									}
								};
								let URL = getU('', Model);
								if (Model.__setting__.predicate.length != 0) {
									URL += '(';
									while (Model.__setting__.predicate.length != 0) {
										if (Model.__setting__.predicate.length == 1) {
											URL += Model.__setting__.predicate.shift() + ')';
											return URL;
										} else {
											URL += Model.__setting__.predicate.shift() + ';';
										}
									}
								}
								return URL;
							}).join(','));
							return this;
						},
						$select: function (...params) {
							this.__setting__.predicate.push('$select=' + params.join(','));
							return this;
						},
						$orderby: function (el, d) {
							this.__setting__.predicate.push(d ? '$orderby=' + el + ' ' + d : '$orderby=' + el);
							return this;
						},
						$top: function (int) {
							this.__setting__.predicate.push('$top=' + int);
							return this;
						},
						$skip: function (int) {
							this.__setting__.predicate.push('$skip=' + int);
							return this;
						},
						$count: function (bool) {
							this.__setting__.predicate.push('$count=' + bool);
							return this;
						},
						$resultFormat: function () {
							this.__setting__.predicate.push('$resultFormat=dataArray');
							return this;
						},
						$filter: function (str) {
							this.__setting__.predicate.push('$filter=' + str);
							return this;
						}
					};
					this.fn.modelObject.prototype = this.fn;
					return new this.fn.modelObject(Array.prototype.slice.call(arguments));
				}
			}
			class Service {
				constructor(serverDomain) {
					this.serverDomain = serverDomain;
					this.setDomain = (URL) => {
						this.serverDomain = URL;
						return this;
					};
					this.getURL = (Model) => {
						return getUrl(Model);
					};
					this.getFormatData = (Model, Domain = false, fn = false) => {
						if (typeof Domain === "function" || !Domain) {
							fn = Domain;
							Domain = this.serverDomain;
						}
						return getFormatData(Model, Domain, fn);
					};
					this.extend = (func) => {
						Object.assign(this, func);
					};

					let highLevel = {
						//high_level_func //新增 
						getThingsByThingsProperties: function (properties, serviceURL = this.serverDomain, fn = false) {
							if (typeof serviceURL === "function") {
								fn = serviceURL;
								serviceURL = this.serverDomain;
							}
							if (serviceURL.endsWith('/') !== true) {
								serviceURL = serviceURL + "/";
							}
							let Fword = "properties/";
							for (var i = 0; i < properties.length - 1; i++) {
								Fword = Fword + properties[i].properties + " eq '" + properties[i].characteristic + "' and properties/";
							}
							Fword = Fword + properties[i].properties + " eq '" + properties[i].characteristic + "'"
							let requestURL = serviceURL + "Things?$count=true&$filter=" + Fword;
							return this.getThings(requestURL, fn);
						},

						getDatastreamsAndObservationsByThingsProperties: function (properties, serviceURL = this.serverDomain, fn = false) {
							if (typeof serviceURL === "function") {
								fn = serviceURL;
								serviceURL = this.serverDomain;
							}
							if (serviceURL.endsWith('/') !== true) {
								serviceURL = serviceURL + "/"
							}
							let Fword = "Thing/properties/";
							for (var i = 0; i < properties.length - 1; i++) {
								Fword = Fword + properties[i].properties + " eq '" + properties[i].characteristic + "' and Thing/properties/";
							}
							Fword = Fword + properties[i].properties + " eq '" + properties[i].characteristic + "' ";
							var requestURL = serviceURL + "Datastreams?$expand=Observations&$filter=" + Fword + " &$count=true";
							return this.getDatastreams(requestURL, fn);
						},
						getDatastreamsAndObservationsByObservedPropertyAndThingsProperties: function (ObservedProperty, properties, serviceURL = this.serverDomain, fn = false) {
							if (typeof serviceURL === "function") {
								fn = serviceURL;
								serviceURL = this.serverDomain;
							}
							if (serviceURL.endsWith('/') !== true) {
								serviceURL = serviceURL + "/"
							}
							let Fword = "Thing/properties/";
							for (var i = 0; i < properties.length - 1; i++) {
								Fword = Fword + properties[i].properties + " eq '" + properties[i].characteristic + "' and Thing/properties/";
							}
							Fword = Fword + properties[i].properties + " eq '" + properties[i].characteristic + "' ";
							var requestURL = serviceURL + "Datastreams?$expand=Observations($orderby=phenomenonTime desc)&$filter=ObservedProperties/name eq '" + ObservedProperty + "' and " + Fword + "&$count=true";
							return this.getDatastreams(requestURL, fn);
						},
						getThingsAndLatestObservationByLocationPolygonAndObservedProperty: function (polygon, ObservedProperty, serviceURL = this.serverDomain, fn = false) {
							if (typeof serviceURL === "function") {
								fn = serviceURL;
								serviceURL = this.serverDomain;
							}
							if (serviceURL.endsWith('/') !== true) {
								serviceURL = serviceURL + "/"
							}
							let requestURL = serviceURL + "Things?$expand=Datastreams($filter=Datastreams/ObservedProperty/name eq '" + ObservedProperty + "'),Datastreams/Observations($orderby=phenomenonTime desc;$top=1),Datastreams/ObservedProperty,Locations&$filter=geo.intersects(Locations/location,geography'" + polygon + "')";
							return this.getThings(requestURL, fn);
						},

						getDatastreamAndObservationsByDatastreamIDAndTimePeriod: function (ID, TimePeriod, serviceURL = this.serverDomain, fn = false) {
							if (typeof serviceURL === "function") {
								fn = serviceURL;
								serviceURL = this.serverDomain;
							}
							if (serviceURL.endsWith('/') !== true) {
								serviceURL = serviceURL + "/";
							}
							let requestURL = serviceURL + "Datastreams(" + ID + ")?$expand=Observations($filter=Observations/phenomenonTime ge " + TimePeriod.start + " and phenomenonTime le " + TimePeriod.end + ";$orderby=Observations/phenomenonTime asc)";
							return this.getDatastreams(requestURL, fn)
						},
						getDatastreamsAndObservationsByObservedPropertyAndTimePeriod: function (ObservedProperty, TimePeriod, serviceURL = this.serverDomain, fn = false) {
							if (typeof serviceURL === "function") {
								fn = serviceURL;
								serviceURL = this.serverDomain;
							}
							if (serviceURL.endsWith('/') !== true) {
								serviceURL = serviceURL + "/";
							}
							let requestURL = serviceURL + "Datastreams?$expand=Observations($filter=phenomenonTime ge " + TimePeriod.start + " and phenomenonTime le " + TimePeriod.end + ";$count=true;$orderby=phenomenonTime asc)&$filter=ObservedProperties/name eq '" + ObservedProperty + "'"
							return this.getDatastreams(requestURL, fn)

						},

						getDatastreamsAndObservationsByThingIDAndTimePeriod: function (ID, TimePeriod, serviceURL = this.serverDomain, fn = false) {
							if (typeof serviceURL === "function") {
								fn = serviceURL;
								serviceURL = this.serverDomain;
							}
							if (serviceURL.endsWith('/') !== true) {
								serviceURL = serviceURL + "/";
							}
							let requestURL = serviceURL + "Datastreams?$expand=Observations($filter=phenomenonTime ge " + TimePeriod.start + " and phenomenonTime le " + TimePeriod.end + ";$count=true;$orderby=phenomenonTime asc)&$filter=Things/id eq '" + ID + "'&$orderby=Datastream/id"
							return this.getDatastreams(requestURL, fn)
						},

						getThingsByObservedPropertyAndLocationPolygonAndTimePeriod: function (ObservedProperty, polygon, TimePeriod, serviceURL = this.serverDomain, fn = false) {
							if (typeof serviceURL === "function") {
								fn = serviceURL;
								serviceURL = this.serverDomain;
							}
							if (serviceURL.endsWith('/') !== true) {
								serviceURL = serviceURL + "/";
							}
							let requestURL = serviceURL + "Things?$expand=Datastreams($filter=Datastreams/ObservedProperty/name eq '" + ObservedProperty + "'),Datastreams/Observations($filter=phenomenonTime ge " + TimePeriod.start + " and phenomenonTime le " + TimePeriod.end + ";$count=true;$orderby=phenomenonTime asc),Locations&$filter=geo.intersects(Locations/location,geography'" + polygon + "')";
							return this.getThings(requestURL, fn)
						},
						//getAll 

						getThings: function (serviceURL = this.serverDomain, fn = false) {
							if (typeof serviceURL === "function") {
								fn = serviceURL;
								serviceURL = this.serverDomain;
							}
							if (serviceURL.indexOf("/Things") != -1) {
								var requestURL = serviceURL;
							} else {
								if (serviceURL.endsWith('/') == true) {
									var requestURL = serviceURL + "Things";
								} else {
									var requestURL = serviceURL + "/Things";
								}
							}
							if (serviceURL != "") {
								return this.getFormatData(requestURL, fn);
							} else {
								return new Error("No URL");
							}
						},

						getLocations: function (serviceURL = this.serverDomain, fn = false) {
							if (typeof serviceURL === "function") {
								fn = serviceURL;
								serviceURL = this.serverDomain;
							}
							if (serviceURL.indexOf("/Locations") != -1) {
								var requestURL = serviceURL;
							} else {
								if (serviceURL.endsWith('/') == true) {
									var requestURL = serviceURL + "Locations";
								} else {
									var requestURL = serviceURL + "/Locations";
								}
							}
							if (serviceURL != "") {
								return this.getFormatData(requestURL, fn);
							} else {
								return new Error("No URL");
							}
						},

						getHistoricalLocations: function (serviceURL = this.serverDomain, fn = false) {
							if (typeof serviceURL === "function") {
								fn = serviceURL;
								serviceURL = this.serverDomain;
							}
							if (serviceURL.indexOf("/HistoricalLocations") != -1) {
								var requestURL = serviceURL;
							} else {
								if (serviceURL.endsWith('/') == true) {
									var requestURL = serviceURL + "HistoricalLocations";
								} else {
									var requestURL = serviceURL + "/HistoricalLocations";
								}
							}
							if (serviceURL != "") {
								return this.getFormatData(requestURL, fn);
							} else {
								return new Error("No URL");
							}
						},

						getDatastreams: function (serviceURL = this.serverDomain, fn = false) {
							if (typeof serviceURL === "function") {
								fn = serviceURL;
								serviceURL = this.serverDomain;
							}
							if (serviceURL.indexOf("/Datastreams") != -1) {
								var requestURL = serviceURL;
							} else {
								if (serviceURL.endsWith('/') == true) {
									var requestURL = serviceURL + "Datastreams";
								} else {
									var requestURL = serviceURL + "/Datastreams";
								}
							}
							if (serviceURL != "") {
								return this.getFormatData(requestURL, fn);
							} else {
								return new Error("No URL");
							}
						},

						getSensors: function (serviceURL = this.serverDomain, fn = false) {
							if (typeof serviceURL === "function") {
								fn = serviceURL;
								serviceURL = this.serverDomain;
							}
							if (serviceURL.indexOf("/Sensors") != -1) {
								var requestURL = serviceURL;
							} else {
								if (serviceURL.endsWith('/') == true) {
									var requestURL = serviceURL + "Sensors";
								} else { //傳入URL為原始網站URL
									var requestURL = serviceURL + "/Sensors";
								}
							}
							if (serviceURL != "") {
								return this.getFormatData(requestURL, fn);
							} else {
								return new Error("No URL");
							}
						},

						getObservedProperties: function (serviceURL = this.serverDomain, fn = false) {
							if (typeof serviceURL === "function") {
								fn = serviceURL;
								serviceURL = this.serverDomain;
							}
							if (serviceURL.indexOf("/ObservedProperties") != -1) {
								var requestURL = serviceURL;
							} else {
								if (serviceURL.endsWith('/') == true) {
									var requestURL = serviceURL + "ObservedProperties";
								} else {
									var requestURL = serviceURL + "/ObservedProperties";
								}
							}
							if (serviceURL != "") {
								return this.getFormatData(requestURL, fn);
							} else {
								return new Error("No URL");
							}
						},

						getObservations: function (serviceURL = this.serverDomain, fn = false) {
							if (typeof serviceURL === "function") {
								fn = serviceURL;
								serviceURL = this.serverDomain;
							}
							if (serviceURL.indexOf("/Observations") != -1) {
								var requestURL = serviceURL;
							} else {
								if (serviceURL.endsWith('/') == true) {
									var requestURL = serviceURL + "Observations";
								} else {
									var requestURL = serviceURL + "/Observations";
								}
							}
							if (serviceURL != "") {
								return this.getFormatData(requestURL, fn);
							} else {
								return new Error("No URL");
							}
						},

						getFeaturesOfInterests: function (serviceURL = this.serverDomain, fn = false) {
							if (typeof serviceURL === "function") {
								fn = serviceURL;
								serviceURL = this.serverDomain;
							}
							if (serviceURL.indexOf("/FeaturesOfInterest") != -1) {
								var requestURL = serviceURL;
							} else {
								if (serviceURL.endsWith('/') == true) {
									var requestURL = serviceURL + "FeaturesOfInterest";
								} else {
									var requestURL = serviceURL + "/FeaturesOfInterest";
								}
							}
							if (serviceURL != "") {
								return this.getFormatData(requestURL, fn);
							} else {
								return new Error("No URL");
							}
						},

						//getByID

						getThingByID: function (dataID, serviceURL = this.serverDomain, fn = false) {
							if (typeof serviceURL === "function") {
								fn = serviceURL;
								serviceURL = this.serverDomain;
							}
							if (serviceURL.endsWith('/') == true) {
								var requestURL = serviceURL + "Things(" + dataID + ")";
							} else {
								var requestURL = serviceURL + "/Things(" + dataID + ")";
							}
							if (serviceURL != "") {
								return this.getFormatData(requestURL, fn);
							} else {
								return new Error("No URL");
							}
						},

						getLocationByID: function (dataID, serviceURL = this.serverDomain, fn = false) {
							if (typeof serviceURL === "function") {
								fn = serviceURL;
								serviceURL = this.serverDomain;
							}
							if (serviceURL.endsWith('/') == true) { //傳入URL為/結尾
								var requestURL = serviceURL + "Locations(" + dataID + ")";
							} else { //傳入URL為原始網站URL
								var requestURL = serviceURL + "/Locations(" + dataID + ")";
							}
							if (serviceURL != "") {
								return this.getFormatData(requestURL, fn);
							} else {
								return new Error("No URL");
							}
						},

						getHistoricalLocationByID: function (dataID, serviceURL = this.serverDomain, fn = false) {
							if (typeof serviceURL === "function") {
								fn = serviceURL;
								serviceURL = this.serverDomain;
							}
							if (serviceURL.endsWith('/') == true) {
								var requestURL = serviceURL + "HistoricalLocations(" + dataID + ")";
							} else {
								var requestURL = serviceURL + "/HistoricalLocations(" + dataID + ")";
							}
							if (serviceURL != "") {
								return this.getFormatData(requestURL, fn);
							} else {
								return new Error("No URL");
							}
						},

						getDatastreamByID: function (dataID, serviceURL = this.serverDomain, fn = false) {
							if (typeof serviceURL === "function") {
								fn = serviceURL;
								serviceURL = this.serverDomain;
							}
							if (serviceURL.endsWith('/') == true) {
								var requestURL = serviceURL + "Datastreams(" + dataID + ")";
							} else {
								var requestURL = serviceURL + "/Datastreams(" + dataID + ")";
							}
							if (serviceURL != "") {
								return this.getFormatData(requestURL, fn);
							} else {
								return new Error("No URL");
							}
						},

						getSensorByID: function (dataID, serviceURL = this.serverDomain, fn = false) {
							if (typeof serviceURL === "function") {
								fn = serviceURL;
								serviceURL = this.serverDomain;
							}
							if (serviceURL.endsWith('/') == true) { //傳入URL為/結尾
								var requestURL = serviceURL + "Sensors(" + dataID + ")";
							} else { //傳入URL為原始網站URL
								var requestURL = serviceURL + "/Sensors(" + dataID + ")";
							}
							if (serviceURL != "") {
								return this.getFormatData(requestURL, fn);
							} else {
								return new Error("No URL");
							}
						},

						getObservedPropertyByID: function (dataID, serviceURL = this.serverDomain, fn = false) {
							if (typeof serviceURL === "function") {
								fn = serviceURL;
								serviceURL = this.serverDomain;
							}
							if (serviceURL.endsWith('/') == true) { //傳入URL為/結尾
								var requestURL = serviceURL + "ObservedProperties(" + dataID + ")";
							} else { //傳入URL為原始網站URL
								var requestURL = serviceURL + "/ObservedProperties(" + dataID + ")";
							}
							if (serviceURL != "") {
								return this.getFormatData(requestURL, fn);
							} else {
								return new Error("No URL");
							}
						},

						getObservationByID: function (dataID, serviceURL = this.serverDomain, fn = false) {
							if (typeof serviceURL === "function") {
								fn = serviceURL;
								serviceURL = this.serverDomain;
							}
							if (serviceURL.endsWith('/') == true) { //傳入URL為/結尾
								var requestURL = serviceURL + "Observations(" + dataID + ")";
							} else { //傳入URL為原始網站URL
								var requestURL = serviceURL + "/Observations(" + dataID + ")";
							}
							if (serviceURL != "") {
								return this.getFormatData(requestURL, fn);
							} else {
								return new Error("No URL");
							}
						},

						getFeaturesOfInterestByID: function (dataID, serviceURL = this.serverDomain, fn = false) {
							if (typeof serviceURL === "function") {
								fn = serviceURL;
								serviceURL = this.serverDomain;
							}
							if (serviceURL.endsWith('/') == true) { //傳入URL為/結尾
								var requestURL = serviceURL + "FeaturesOfInterest(" + dataID + ")";
							} else { //傳入URL為原始網站URL
								var requestURL = serviceURL + "/FeaturesOfInterest(" + dataID + ")";
							}
							if (serviceURL != "") {
								return this.getFormatData(requestURL, fn);
							} else {
								return new Error("No URL");
							}
						},
						//high-level          

						getThingsByLocationID: function (dataID, serviceURL = this.serverDomain, fn = false) {
							if (typeof serviceURL === "function") {
								fn = serviceURL;
								serviceURL = this.serverDomain;
							}
							if (serviceURL.endsWith('/') == true) { //傳入URL為/結尾
								var requestURL = serviceURL + "Locations(" + dataID + ")/Things";
							} else { //傳入URL為原始網站URL
								var requestURL = serviceURL + "/Locations(" + dataID + ")/Things";
							}
							if (serviceURL != "") {
								return this.getFormatData(requestURL, fn);
							} else {
								return new Error("No URL");
							}
						},

						getThingByHistoricalLocationID: function (dataID, serviceURL = this.serverDomain, fn = false) {
							if (typeof serviceURL === "function") {
								fn = serviceURL;
								serviceURL = this.serverDomain;
							}
							if (serviceURL.endsWith('/') == true) { //傳入URL為/結尾
								var requestURL = serviceURL + "HistoricalLocations(" + dataID + ")/Thing";
							} else {
								var requestURL = serviceURL + "/HistoricalLocations(" + dataID + ")/Thing";
							}
							if (serviceURL != "") {
								return this.getFormatData(requestURL, fn);
							} else {
								return new Error("No URL");
							}
						},

						getThingByDatastreamID: function (dataID, serviceURL = this.serverDomain, fn = false) {
							if (typeof serviceURL === "function") {
								fn = serviceURL;
								serviceURL = this.serverDomain;
							}
							if (serviceURL.endsWith('/') == true) {
								var requestURL = serviceURL + "Datastreams(" + dataID + ")/Thing";
							} else {
								var requestURL = serviceURL + "/Datastreams(" + dataID + ")/Thing";
							}
							if (serviceURL != "") {
								return this.getFormatData(requestURL, fn);
							} else {
								return new Error("No URL");
							}
						},

						getLocationsByThingID: function (dataID, serviceURL = this.serverDomain, fn = false) {
							if (typeof serviceURL === "function") {
								fn = serviceURL;
								serviceURL = this.serverDomain;
							}
							if (serviceURL.endsWith('/') == true) { //傳入URL為/結尾
								var requestURL = serviceURL + "Things(" + dataID + ")/Locations";
							} else { //傳入URL為原始網站URL
								var requestURL = serviceURL + "/Things(" + dataID + ")/Locations";
							}
							if (serviceURL != "") {
								return this.getFormatData(requestURL, fn);
							} else {
								return new Error("No URL");
							}
						},

						getLocationsByHistoricalLocationID: function (dataID, serviceURL = this.serverDomain, fn = false) {
							if (typeof serviceURL === "function") {
								fn = serviceURL;
								serviceURL = this.serverDomain;
							}
							if (serviceURL.endsWith('/') == true) {
								var requestURL = serviceURL + "HistoricalLocations(" + dataID + ")/Locations";
							} else {
								var requestURL = serviceURL + "/HistoricalLocations(" + dataID + ")/Locations";
							}
							if (serviceURL != "") {
								return this.getFormatData(requestURL, fn);
							} else {
								return new Error("No URL");
							}
						},

						getHistoricalLocationsByThingID: function (dataID, serviceURL = this.serverDomain, fn = false) {
							if (typeof serviceURL === "function") {
								fn = serviceURL;
								serviceURL = this.serverDomain;
							}
							if (serviceURL.endsWith('/') == true) {
								var requestURL = serviceURL + "Things(" + dataID + ")/HistoricalLocations";
							} else {
								var requestURL = serviceURL + "/Things(" + dataID + ")/HistoricalLocations";
							}
							if (serviceURL != "") {
								return this.getFormatData(requestURL, fn);
							} else {
								return new Error("No URL");
							}
						},

						getHistoricalLocationsByLocationID: function (dataID, serviceURL = this.serverDomain, fn = false) {
							if (typeof serviceURL === "function") {
								fn = serviceURL;
								serviceURL = this.serverDomain;
							}
							if (serviceURL.endsWith('/') == true) {
								var requestURL = serviceURL + "Locations(" + dataID + ")/HistoricalLocations";
							} else {
								var requestURL = serviceURL + "/Locations(" + dataID + ")/HistoricalLocations";
							}
							if (serviceURL != "") {
								return this.getFormatData(requestURL, fn);
							} else {
								return new Error("No URL");
							}
						},

						getDatastreamsByThingID: function (dataID, serviceURL = this.serverDomain, fn = false) {
							if (typeof serviceURL === "function") {
								fn = serviceURL;
								serviceURL = this.serverDomain;
							}
							if (serviceURL.endsWith('/') == true) {
								var requestURL = serviceURL + "Things(" + dataID + ")/Datastreams";
							} else {
								var requestURL = serviceURL + "/Things(" + dataID + ")/Datastreams";
							}
							if (serviceURL != "") {
								return this.getFormatData(requestURL, fn);
							} else {
								return new Error("No URL");
							}
						},
						getDatastreamsBySensorID: function (dataID, serviceURL = this.serverDomain, fn = false) {
							if (typeof serviceURL === "function") {
								fn = serviceURL;
								serviceURL = this.serverDomain;
							}
							if (serviceURL.endsWith('/') == true) { //傳入URL為/結尾
								var requestURL = serviceURL + "Sensors(" + dataID + ")/Datastreams";
							} else { //傳入URL為原始網站URL
								var requestURL = serviceURL + "/Sensors(" + dataID + ")/Datastreams";
							}
							if (serviceURL != "") {
								return this.getFormatData(requestURL, fn);
							} else {
								return new Error("No URL");
							}
						},

						getDatastreamByObservedPropertyID: function (dataID, serviceURL = this.serverDomain, fn = false) {
							if (typeof serviceURL === "function") {
								fn = serviceURL;
								serviceURL = this.serverDomain;
							}
							if (serviceURL.endsWith('/') == true) { //傳入URL為/結尾
								var requestURL = serviceURL + "ObservedProperties(" + dataID + ")/Datastreams";
							} else { //傳入URL為原始網站URL
								var requestURL = serviceURL + "/ObservedProperties(" + dataID + ")/Datastreams";
							}
							if (serviceURL != "") {
								return this.getFormatData(requestURL, fn);
							} else {
								return new Error("No URL");
							}
						},

						getDatastreamByObservationID: function (dataID, serviceURL = this.serverDomain, fn = false) {
							if (typeof serviceURL === "function") {
								fn = serviceURL;
								serviceURL = this.serverDomain;
							}
							if (serviceURL.endsWith('/') == true) { //傳入URL為/結尾
								var requestURL = serviceURL + "Observations(" + dataID + ")/Datastream";
							} else { //傳入URL為原始網站URL
								var requestURL = serviceURL + "/Observations(" + dataID + ")/Datastream";
							}
							if (serviceURL != "") {
								return this.getFormatData(requestURL, fn);
							} else {
								return new Error("No URL");
							}
						},

						getSensorByDatastreamID: function (dataID, serviceURL = this.serverDomain, fn = false) {
							if (typeof serviceURL === "function") {
								fn = serviceURL;
								serviceURL = this.serverDomain;
							}
							if (serviceURL.endsWith('/') == true) { //傳入URL為/結尾
								var requestURL = serviceURL + "Datastreams(" + dataID + ")/Sensor";
							} else { //傳入URL為原始網站URL
								var requestURL = serviceURL + "/Datastreams(" + dataID + ")/Sensor";
							}
							if (serviceURL != "") {
								return this.getFormatData(requestURL, fn);
							} else {
								return new Error("No URL");
							}
						},

						getObservedPropertyByDatastreamID: function (dataID, serviceURL = this.serverDomain, fn = false) {
							if (typeof serviceURL === "function") {
								fn = serviceURL;
								serviceURL = this.serverDomain;
							}
							if (serviceURL.endsWith('/') == true) { //傳入URL為/結尾
								var requestURL = serviceURL + "Datastreams(" + dataID + ")/ObservedProperty";
							} else { //傳入URL為原始網站URL
								var requestURL = serviceURL + "/Datastreams(" + dataID + ")/ObservedProperty";
							}
							if (serviceURL != "") {
								return this.getFormatData(requestURL, fn);
							} else {
								return new Error("No URL");
							}
						},

						getObservationsByDatastreamID: function (dataID, serviceURL = this.serverDomain, fn = false) {
							if (typeof serviceURL === "function") {
								fn = serviceURL;
								serviceURL = this.serverDomain;
							}
							if (serviceURL.endsWith('/') == true) { //傳入URL為/結尾
								var requestURL = serviceURL + "Datastreams(" + dataID + ")/Observations";
							} else { //傳入URL為原始網站URL
								var requestURL = serviceURL + "/Datastreams(" + dataID + ")/Observations";
							}
							if (serviceURL != "") {
								return this.getFormatData(requestURL, fn);
							} else {
								return new Error("No URL");
							}
						},

						getObservationsByFeatureOfInterestID: function (dataID, serviceURL = this.serverDomain, fn = false) {
							if (typeof serviceURL === "function") {
								fn = serviceURL;
								serviceURL = this.serverDomain;
							}
							if (serviceURL.endsWith('/') == true) { //傳入URL為/結尾
								var requestURL = serviceURL + "FeaturesOfInterest(" + dataID + ")/Observations";
							} else { //傳入URL為原始網站URL
								var requestURL = serviceURL + "/FeaturesOfInterest(" + dataID + ")/Observations";
							}
							if (serviceURL != "") {
								return this.getFormatData(requestURL, fn);
							} else {
								return new Error("No URL");
							}
						},

						getFeatureOfInterestByObservationID: function (dataID, serviceURL = this.serverDomain, fn = false) {
							if (typeof serviceURL === "function") {
								fn = serviceURL;
								serviceURL = this.serverDomain;
							}
							if (serviceURL.endsWith('/') == true) { //傳入URL為/結尾
								var requestURL = serviceURL + "Observations(" + dataID + ")/FeatureOfInterest";
							} else { //傳入URL為原始網站URL
								var requestURL = serviceURL + "/Observations(" + dataID + ")/FeatureOfInterest";
							}
							if (serviceURL != "") {
								return this.getFormatData(requestURL, fn);
							} else {
								return new Error("No URL");
							}
						}
					};
					return Object.assign(this, highLevel, extendObjService);
				}
			}
			class Display {
				constructor() {
                    this.drawMap = (mapID, resultSet,fn) => {
                        let Dis = this;
                        if (typeof L === 'undefined') {
                            var leafletjs = document.createElement('script');
                            leafletjs.setAttribute("src", "https://unpkg.com/leaflet@1.5.1/dist/leaflet.js");
                            document.getElementsByTagName('head')[0].appendChild(leafletjs);
                            var leafletCSS = document.createElement('link');
                            leafletCSS.setAttribute("rel", "stylesheet");
                            leafletCSS.setAttribute("type", "text/css");
                            leafletCSS.setAttribute("href", "https://unpkg.com/leaflet@1.5.1/dist/leaflet.css");
                            document.getElementsByTagName('head')[0].appendChild(leafletCSS);
                            if(leafletjs.readyState){
                                leafletjs.onreadystastechange = function(mapID, resultSet){
                                    if(leafletjs.readyState ==='loaded'||leafletjs.readyState ==='complete'){
                                        leafletjs.onreadystastechange = null;
                                        return Dis.Map(mapID, resultSet,fn )
                                    }
                                }
                            } else{
                                leafletjs.onload = function(){  
                                    return Dis.Map(mapID , resultSet,fn)    
                                }
                            }
					   } else {
                           return Dis.Map(mapID , resultSet,fn)
                       }
                       
					};
					this.drawChart = (chartID, serviceURL, ID = null, TimePeriod = null) => { 	   
                        let Dis = this;
                        if(typeof Highcharts === 'undefined'){
                            var Highchartsjs = document.createElement('script');
                            Highchartsjs.setAttribute("src", "http://code.highcharts.com/highcharts.js");
                            document.getElementsByTagName('head')[0].appendChild(Highchartsjs);  
                            if(Highchartsjs.readyState){
                                Highchartsjs.onreadystastechange = function(chartID, serviceURL, ID , TimePeriod){
                                    if(Highchartsjs.readyState ==='loaded'||Highchartsjs.readyState ==='complete'){
                                        Highchartsjs.onreadystastechange = null;
                                        return Dis.Chart(chartID, serviceURL, ID , TimePeriod )
                                    }
                                }
                            } else{
                                Highchartsjs.onload = function(){
                                    return Dis.Chart(chartID, serviceURL, ID , TimePeriod )
                                }
                            }
                        } else {
                            return Dis.Chart(chartID, serviceURL, ID , TimePeriod )
                        }
                        
                    
                    };
                    this.Map = (mapID,resultSet,fn = false) => { 
                        let pinIcon = L.icon({
							iconUrl: 'https://cdn2.iconfinder.com/data/icons/iconslandgps/PNG/256x256/Pinpoints/NeedleLeftYellow.png',
							iconSize: [64, 64],
							iconAnchor: [32, 64],
							popupAnchor: [-18, -42]
						});
						var vectorLocation = L.layerGroup([]);
						if (resultSet.value.length == 0) {
							return new Error("此筆ResultSet為空");
						}
						if (resultSet.value[0].Location.length == 0) {
							throw new Error("此筆ResultSet未存有Locations");
						} else {
							let map = new L.Map(mapID); // a global variable for the map object
							var isMarkerAdded = false; // a global variable for knowing if the marker has been added
							// 設定經緯度座標	
							map.setView(new L.LatLng(24.210, 120.350), 7);
							// 設定圖資來源
							var osmUrl = 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png';
							var osm = new L.TileLayer(osmUrl, {
								minZoom: 7,
								maxZoom: 12
							});
							map.addLayer(osm);
							for (let i = 0; i < resultSet.value.length; i++) {
								if (resultSet.value[i].Datastream.length == 0) {
									var message = "<label>" + resultSet.value[i].name + "</label>";
									var point = L.marker([resultSet.value[i].Location[0].location.coordinates[1], resultSet.value[i].Location[0].location.coordinates[0]], {
										icon: pinIcon
									}).bindPopup(message);
									vectorLocation.addLayer(point);
								} else {
									var message = "<label>" + resultSet.value[i].name + "</label><br>"
									for (let j = 0; j < resultSet.value[i].Datastream.length; j++) {
										if (resultSet.value[i].Datastream[j].Observation.length == 0) {
											continue;
										}
										message = message + "<label>觀測值為:" + resultSet.value[i].Datastream[j].name + " " + resultSet.value[i].Datastream[j].Observation[0].result + resultSet.value[i].Datastream[j].unitOfMeasurement.symbol + " 最後觀測時間:" + resultSet.value[i].Datastream[j].Observation[0].phenomenonTime + "</laber><br>"
									}
									var point = L.marker([resultSet.value[i].Location[0].location.coordinates[1], resultSet.value[i].Location[0].location.coordinates[0]], {
										icon: pinIcon
									}).bindPopup(message, {
										maxWidth: 560
									});

									vectorLocation.addLayer(point);
								}
							}
						map.addLayer(vectorLocation);
                        }
						return (fn == false) ? vectorLocation._map : fn(vectorLocation._map);  
                    };
                    this.Chart = (chartID,serviceURL,ID,TimePeriod) => {
                        document.getElementById(chartID).value = "";
						var xData = []; //給定每個series的名稱及觀測單位
						var yData = []; //給定每個series的data及pointStart,每次concat就要重製
						var series = []; //要累積
						if (typeof serviceURL === 'object' && ID == null && TimePeriod == null) { //傳入的是resultSet物件且是Datastream
							var resultSet = serviceURL;
							for (var i = 0; i < resultSet.value.length; i++) {
								if (resultSet.value[i].Observation.length == 0) { //目前解法:進入value迴圈時一圈一圈判斷,若無則跳過
									console.log("ID:" + resultSet.value[i].id + "之Datastream無Observations")
									continue;
								}
								xData[i] = {
									name: resultSet.value[i].description + "(ID:" + resultSet.value[i].id + ")",
									symbol: resultSet.value[i].unitOfMeasurement.symbol
								};
								for (let j = 0; j < resultSet.value[i].Observation.length; j++) {
									var time = new Date(resultSet.value[i].Observation[j].phenomenonTime.split(".")[0]).getTime() + (8 * 60 * 60 * 1000); //時區+8hr
									yData[j] = [time, resultSet.value[i].Observation[j].result];
								}
								yData.sort();
								var seriesData = [{
									name: xData[i].name,
									data: yData,
									pointStart: yData[0][0],
									tooltip: {
										valueSuffix: xData[i].symbol,
									}
								}];
								yData = [];
								series = series.concat(seriesData);
							}

						}

						if (typeof serviceURL === 'string' && ID !== null && TimePeriod !== null) { //傳入的是URL及ID與TimePeriod
							if (typeof ID === 'string') { //若ID為string,表示為單筆
								let array = [];
								array.push(ID);
								ID = array;
							}
							if (serviceURL.endsWith('/') !== true) {
								serviceURL = serviceURL + "/";
							}
							for (let i = 0; i < ID.length; i++) {
								if (TimePeriod.length == 1) { //每筆Datastream(ID)共用同一TimePeriod
									var requestURL = serviceURL + "Datastreams?$expand=Observations($filter=Observations/phenomenonTime ge " + TimePeriod[0].start + " and Observations/phenomenonTime le " + TimePeriod[0].end + ";$orderby=Observations/phenomenonTime asc) &$filter=Datastreams/id eq " + ID[i];
								}
								if (TimePeriod.length > 1) { //每筆Datastream(ID)都有自己的TimePeriod
									if (ID.length == TimePeriod.length) {
										var requestURL = serviceURL + "Datastreams?$expand=Observations($filter=Observations/phenomenonTime ge " + TimePeriod[i].start + " and Observations/phenomenonTime le " + TimePeriod[i].end + ";$orderby=Observations/phenomenonTime asc) &$filter=Datastreams/id eq " + ID[i];
									} else {
										throw new Error("ID與TimePeriod數目不符")
									}
								}
								let resultSet = getFormatData(requestURL);
								if (resultSet.value.length == 0 || resultSet.value[0].Observation.length == 0) { //若此ID無Datastream或此時間段之Datastream無Observations
									console.log("無ID為" + ID[i] + "之Datastream或此時間段之Datastream無Observations")
									continue;
								}
								xData[i] = {
									name: resultSet.value[0].description + "(ID:" + resultSet.value[0].id + ")",
									symbol: resultSet.value[0].unitOfMeasurement.symbol
								};
								for (var j = 0; j < resultSet.value[0].Observation.length; j++) { //設定series的phenomenonTime
									var time = new Date(resultSet.value[0].Observation[j].phenomenonTime.split(".")[0]).getTime() + (8 * 60 * 60 * 1000);
									yData[j] = [time, resultSet.value[0].Observation[j].result];
								}
								yData.sort();
								var seriesData = [{
									name: xData[i].name,
									data: yData,
									pointStart: yData[0][0],
									tooltip: {
										valueSuffix: resultSet.value[0].unitOfMeasurement.symbol,
									},
								}];
								yData = [];
								series = series.concat(seriesData);
							}
						}
						let C = new Highcharts.Chart({
							chart: {
								renderTo: chartID,
								type: 'line'
							},
							title: {
								text: ""
							},
							xAxis: {
								type: 'datetime',

							},
							legend: {
								layout: 'vertical',
								align: 'right',
								verticalAlign: 'middle',
								borderWidth: 0,
							},
							series: series
						});
						return C
                    };
					return Object.assign(this, extendObjDisplay);
				}
			}

			let cleanStringify = (object) => {
				let copyWithoutCircularReferences = (references, object) => {
					let cleanObject = {};
					Object.keys(object).forEach(key => {
						let value = object[key];
						if (value && typeof value === 'object') {
							if (references.indexOf(value) < 0) {
								references.push(value);
								if (Array.isArray(value)) {
									if (value.length > 0) {
										cleanObject[key] = value.map(i => {
											return (references.indexOf(i) < 0) ? copyWithoutCircularReferences(references, i) : '###_Circular_###';
										});
									}
								} else {
									cleanObject[key] = copyWithoutCircularReferences(references, value);
								}
								references.pop();
							} else {
								cleanObject[key] = '###_Circular_###';
							}
						} else if (typeof value !== 'function') {
							cleanObject[key] = value;
						}
					});
					return cleanObject;
				};
				if (object && typeof object === 'object') {
					object = copyWithoutCircularReferences([object], object);
				}
				return JSON.stringify(object, undefined, "\t");
			};

			let getUrl = (Model) => {
				let getSubject = (model, subjectArray = []) => {
					let parent = model.__setting__.parent;
					if (parent != undefined && Array.isArray(model[parent.type]) && !Array.isArray(parent[model.type])) {
						subjectArray.push(model.type);
						subjectArray = getSubject(parent, subjectArray);
					} else if (model.id != undefined) {
						subjectArray.push(model.type + 's(' + model.id + ')');
					} else if (subjectArray.length <= 1) {
						subjectArray.push(model.type + 's');
						subjectArray = (parent != undefined) ? getSubject(parent, subjectArray) : subjectArray;
					} else {
						throw new Error(`The URL is not valid.`);
					}
					return subjectArray;
				};

				let setting = Model.__setting__;
				let URL = getSubject(Model).reverse().join("/");
				if (setting.predicate.length != 0) {
					URL += '?';
					while (Model.__setting__.predicate.length != 0) {
						if (setting.predicate.length == 1) {
							URL += setting.predicate.shift();
							return URL;
						} else {
							URL += setting.predicate.shift() + '&';
						}
					}
				}
				return URL;
			};

			let getModelArray = (requestURL) => {
				let re = new RegExp(new Model("__status__").modelObject.join("|"));
				let URL = requestURL.split("?");
				let stop = true;
				let modelArray = URL[0].split("/").reverse().filter(i => {
					if (re.test(i) && stop) {
						return true;
					} else {
						stop = false;
						return stop;
					}
				}).map(i => {
					return {
						model: i.match(re)[0],
						id: (/[\d]{1,}/.test(i)) ? Number(i.match(/[\d]{1,}/)[0]) : undefined
					}
				}).reverse();
				if (modelArray.length > 0) {
					return modelArray;
				} else {
					throw new Error('The URL does not contain any Model.');
				}
			};

			let getModel = (requestURL, preModel = false) => {
				let URL = requestURL.split("?");
				let modelArray = getModelArray(URL[0]);
				let fr = modelArray.shift();
				let frModel = (preModel) ? preModel.Model(fr.model, {
					id: fr.id
				}) : new Model(fr.model, {
					id: fr.id
				});
				if (modelArray.length == 0) {
					return frModel;
				}
				while (modelArray.length != 0) {
					let Se = modelArray.shift();
					if (modelArray.length == 0) {
						let r = frModel.Model(Se.model, {
							id: Se.id
						});
						if (URL[1]) {
							r.__setting__.predicate.push(URL[1])
						};
						return r;
					} else {
						frModel = frModel.Model(Se.model, {
							id: Se.id
						});
					}
				}
			};

			let getFormatData = (eModel, Domain = false, fn = false) => {
				fn = (typeof Domain === "function") ? fn = Domain : fn;
				let changeModel = (type) => {
					switch (type) {
						case "Things":
							return {
								name: "Thing", isModel: true
							};
						case "Locations":
							return {
								name: "Location", isModel: true
							};
						case "HistoricalLocations":
							return {
								name: "HistoricalLocation", isModel: true
							};
						case "Datastreams":
							return {
								name: "Datastream", isModel: true
							};
						case "Sensors":
							return {
								name: "Sensor", isModel: true
							};
						case "ObservedPropertys":
							return {
								name: "ObservedProperty", isModel: true
							};
						case "Observations":
							return {
								name: "Observation", isModel: true
							};
						case "FeaturesOfInterest":
							return {
								name: "FeatureOfInterest", isModel: true
							};
						case "@iot.id":
							return {
								name: "id", isModel: false
							};
						default:
							return (["Thing", "Location", "HistoricalLocation", "Datastream", "Sensor", "ObservedProperty", "Observation", "FeatureOfInterest"].includes(type)) ? {
								name: type,
								isModel: true
							} : {
								name: type,
								isModel: false
							};
					}
				};
				let newModel = (method, setting = undefined) => {
					return this.Model(method, setting);
				};
				let requestURL = (typeof eModel === "object") ? (Domain + getUrl(eModel)) : eModel;
				eModel = (typeof eModel === "object") ? eModel : getModel(eModel);
				let modelType = eModel.type;
				let preModel;
				if (typeof eModel === "object" && eModel.__setting__.parent && eModel.__setting__.parent.id != undefined) {
					preModel = eModel.__setting__.parent;
					if (Array.isArray(eModel.__setting__.parent[eModel.type]) && eModel.id == undefined) {
						eModel.__setting__.parent[eModel.type].length = 0;
					}
				} else {
					preModel = false;
				}

				let resultSet;
				let xmlhttp = window.XMLHttpRequest ? new XMLHttpRequest() : new ActiveXObject("Microsoft.XMLHTTP");
				xmlhttp.onreadystatechange = function () {
					if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
						class ResultSet {
							constructor(nextlink, type, value) {
								this.ini = (nextlink, type, value) => {
									this.nextlink = nextlink;
									this.type = type;
									this.value = value;
									return this;
								};
								this.next = () => {
									//ResultSet中還有資料
									if (this.value.length > 0) {
										return this.value.shift();
									} else if (typeof this.nextlink === 'undefined') {
										throw new Error("已經無資料！");
									} else { //ResultSet中空了，但有下一頁，回傳下一頁
										let resultData = this.getFormatData(this.nextlink);
										this.nextlink = resultData.nextlink;
										this.value = resultData.value;
										return this.value.shift();
									}
								};
								this.hasNext = () => {
									return (this.value.length == 0 && !this.nextlink) ? false : true;
								};
								this.getAllData = (fn) => {
									if (typeof this.nextlink === 'undefined') { //已無下一頁
										if (typeof fn === "function") {
                                            fn(this)
                                        } else {
                                            return this;    
                                        }
									} else {
										if (typeof fn === "function") { //異步
											let R = this;
											getFormatData(this.nextlink, function (resultSet) {
												resultSet.value = resultSet.value.concat(R.value);
												if (typeof resultSet.nextlink !== "undefined") {
													resultSet.getAllData(fn);
												} else {
													fn(resultSet);
												}
											});
										} else {
											while (typeof this.nextlink !== "undefined") {
												let resultData = getFormatData(this.nextlink);
												this.nextlink = resultData.nextlink;
												this.value = this.value.concat(resultData.value);
											}
											return this;
										}
									}
								};
								this.getJSONStringify = () => {
									return cleanStringify(this);
								};
								return this.ini(nextlink, type, value);
							}
						}
						let RF = (Rvalue, Modeltype, preModel = false) => {
							let RK = Object.keys(Rvalue).map(i => {
								let newKey = changeModel(i).name;
								if (i != newKey) {
									Rvalue[newKey] = Rvalue[i];
									delete Rvalue[i];
								}
								return newKey;
							});
							let Model = (preModel != false) ? preModel.Model(Modeltype, {
								id: Rvalue.id
							}) : newModel(Modeltype, {
								id: Rvalue.id
							});
							RK.filter(i => Object.keys(Model).includes(i)).map(j => {
								let t = changeModel(j);
								if (t.isModel) {
									Model[t.name] = (Array.isArray(Rvalue[j])) ? Rvalue[j].map(k => RF(k, t.name, Model), Model) : RF(Rvalue[j], t.name, Model);
								} else {
									Model[t.name] = Rvalue[j];
								}
							});
							return Model;
						};

						let json = JSON.parse(xmlhttp.responseText);
						let value = (typeof json.value === 'undefined') ? [].concat(json) : json.value;
						if (fn) {
							fn(new ResultSet(json["@iot.nextLink"], modelType, value.map(Rvalue => {
								return RF(Rvalue, modelType, preModel);
							})));
						} else {
							resultSet = new ResultSet(json["@iot.nextLink"], modelType, value.map(Rvalue => {
								return RF(Rvalue, modelType, preModel);
							}));
						}
					}
				};
				xmlhttp.open("GET", requestURL, (fn) ? true : false);
				xmlhttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded; charset=UTF-8');
				xmlhttp.send();
				if (xmlhttp.status == 404) {
					return new Error("URL is not valid. !URL：" + requestURL);
				}
				return resultSet;
			};

			let extendObjModel = {};

			let extendObjService = {};

			let extendObjDisplay = {};

			this.extend = (className, extendObject = {}) => {
				switch (className) {
					case 'Model':
						Object.assign(extendObjModel, extendObject);
						return this;
					case 'Service':
						Object.assign(extendObjService, extendObject);
						return this;
					case 'Display':
						Object.assign(extendObjDisplay, extendObject);
						return this;
					default:
						return this;
				}
			};
			this.Model = (method, setting = undefined) => {
				return new Model(method, setting);
			};
			this.Service = (serverDomain = '') => {
				return new Service(serverDomain);
			};
			this.Display = () => {
				return new Display();
			};
			return this;
		}
	}
	window.$TA = new $TA();
})();