
$(function () {
	/**
	 *  autoColSpan
	 *  테이블영역 조회 데이터 없을때
	 *  <td class="autoColSpan"></td>
	 */
	$('.autoColSpan').each(function(){
		var colNum = $(this).parents('table').find('col').length;
		$(this).attr('colSpan', colNum);
	});

});

String.prototype.replaceAll = function (org, dest) {
	return this.split(org).join(dest);
}

/**
 * 공통 Ajax 호출 (중복방지)
 *
 * @param url
 * @param data
 * @param callback
 */
var doubleSubmitFlag = false;
function cmmnAjax(url, data, callback, isDubleSubmitCheck, isFile) {
	var dataType = "json";
	var processData = true;
	var contentType = 'application/x-www-form-urlencoded; charset=UTF-8';

	//중복 submit 체크
	if(isDubleSubmitCheck === true) {
		doubleSubmitFlag = true;
	}

	if(doubleSubmitFlag) {
		alert("처리중입니다");
		return false;
	}


	if(isFile === true){
		processData = false;
		contentType = false;
	}

	//TODO KYJ : 토큰 처리 필요?
	$.ajax({
		url: url,
		type: 'post',
		data: data,
		dataType: dataType,
		processData: processData,
		contentType: contentType,
		async: false,
		success: function (data) {
			if(callback != null) {
				callback(data);
			}
			doubleSubmitFlag = false;
		},
		error: function (e) {
			console.log(e);
			doubleSubmitFlag = false;
		}
	});
}


/**
 * 공통 submit (중복방지)
 *
 * @param obj : $("#form")
 * @param url
 */
var isDuplForm = true;
function cmmnSubmit(obj, url) {
	if(isDuplForm){
		isDuplForm = false;

		//TODO KYJ : 토큰 처리 필요?
		var input = $('<input>').attr('type', 'hidden').attr('name', 'tokenName').val($('#tokenName').val());
		obj.append($(input));
		obj.attr('action', url);
		obj.submit();
	}
}

/**
 * 공통 load (중복방지)
 *
 * @param obj : $("#form")
 * @param url
 */
var isDuplLoad = true;
function cmmnLoad(obj, url, data, callback, isDupleCheck, isLoding) {
	if(isDuplLoad){
		if(isLoding == null || isLoding) {
			fnLodingAppend(obj); // 로딩바 시작
		}

		if(isDupleCheck) {
			isDuplLoad = false;
		}
		$(obj).load(url, data, function(response, status, request) {
			if(status != 'success'){
				alert('처리중 오류가 발생하였습니다.');
				return false;
			}
			if(callback != null) {
				callback(response, status, request);
			}
			isDuplLoad = true;
			$('#lodingArea').remove();
		});
	}
}

/**
 * 객체 복사 (Object -> Object)
 *
 * @param obj
 * @returns object
 */
function cmmnClone(obj) {
	var outputObj = {};
	for(var i in obj) {
		outputObj[i] = obj[i];
	}
	return outputObj;
}


/**
 * json데이터와 id를 가진 태그 value 동기화
 *
 * @param json : json 데이터
 */
function fnSyncJsonToInput(json) {
	for (let key in json) {
		if (key.indexOf("_$") == -1) {
			let tag = $("input[name=" + key + "]");

			//TODO KYJ 2020-06-22 : 체크박스 추가 필요?
			if (tag.prop("type") === "radio") {
				// radio일 경우 name, value로 비교
				$("input[name=" + key + "]:input[value=" + json[key] + "]").attr("checked", true);
			} else {
				let tagId = $("#" + key);
				if (tagId.prop("tagName") === "SPAN") { //
					tagId.html(json[key]);
				} else {
					tagId.val(json[key]);
				}
			}
		}
	}
}

//달력 포맷 정의
var dateFormat = {
	days: ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일'],
	daysShort: ['일', '월', '화', '수', '목', '금', '토'],
	daysMin: ['일', '월', '화', '수', '목', '금', '토'],
	months: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
	monthsShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
	today: 'Today', // Today -> 오늘
	clear: 'Clear', // Clear -> 지우기
	dateFormat: 'yyyy-mm-dd',
	timeFormat: 'hh:ii',
	position: 'bottom right',
	minDate: '',
	maxDate: '',
	timepicker: false,
	autoClose: true,
	confirmButton: false,
	classes: '',
	firstDay: 0,
	clearButton: false
};


/**
 * sVar , eVar 필수값
 * sVar : 시작날짜 ex(2019-08-22)
 * eVar : 종료날짜 ex(2019-09-22)
 * 사용 예 : dynamicDatepicker($('#sDate'), $('#eDate'));
 */
// 동적 datePicker 생성
function dynamicDatepicker(sVar, eVar, customFormat) {
	var date = new Date();
	var format = cmmnClone(dateFormat);

	if(customFormat != null){
		format = customFormat;
	}

	var sdate = $(sVar).datepicker({
		//년-월-일
		startDate: new Date(date.getFullYear(), date.getMonth(), date.getDate()),
		language: 'ko',
		position: format.position,
		dateFormat: format.dateFormat,
		timeFormat: format.timeFormat,
		timepicker: format.timepicker,
		minDate: format.minDate,
		maxDate: format.maxDate,
		autoClose: format.autoClose,
		confirmButton: format.confirmButton,
		classes: format.classes,
		clearButton: format.clearButton,
		//선택한 날짜를 가져옴
		onSelect: function (date) {
			if (date != '') {
				date = date.replace(/[.-]/gi, "/");
				// sdate.hide();
				//종료일 datepicker에 최소날짜를 방금 클릭한 날짜로 설정
				if ($(eVar).val() != "" && $(sVar).val() > $(eVar).val()) {
					alert('시작일이 종료일보다 클 수 없습니다.');
					sdate.clear();
				}
				$(eVar).datepicker({
					minDate: new Date(date)
				});
			}

		}
	}).data('datepicker');


	var edate = $(eVar).datepicker({
		startDate: new Date(date.getFullYear(), date.getMonth(), date.getDate()),  // 시간, 분은 00으로 초기 설정
		language: 'ko',
		position: format.position,
		dateFormat: format.dateFormat,
		timeFormat: format.timeFormat,
		timepicker: format.timepicker,
		minDate: format.minDate,
		maxDate: format.maxDate,
		autoClose: format.autoClose,
		confirmButton: format.confirmButton,
		classes: format.classes,
		clearButton: format.clearButton,
		//선택한 날짜를 가져옴
		onSelect: function (date) {

			if (date != '') {
				date = date.replace(/[.-]/gi, "/");
				// edate.hide();
				//종료일 datepicker에 최소날짜를 방금 클릭한 날짜로 설정
				if ($(sVar).val() != "" && date < $(sVar).val()) {
					alert('종료일이 시작일보다 작을 수 없습니다.');
					edate.clear();
				}
				$(sVar).datepicker({
					//시작일 datepicker에 최대날짜를 방금 클릭한 날짜로 설정
					maxDate: new Date(date)
				});
			}
		}
	}).data('datepicker');

}

/**
 * cmmnIconCheck
 * @param icon
 * @returns {string}
 */
function cmmnIconCheck(icon){
	var icon = icon;
	if(icon == 1){
		icon = "success"
	}else if(icon == 2){
		icon = "error"
	}else if(icon == 3){
		icon = "warning";
	}else if(icon == 4){
		icon = "info";
	}else if(icon == 5){
		icon = "question";
	}else {
		icon = "warning";
	}
	return icon;
}

/**
 * validation alert 창
 * 필수 파라미터 : message
 * 파라미터 : title, iconNum, btnText, callback
 * ex : cmmnAlert('필수값입니다 입력해주세요.',3, '확인' , function(){ $('#searchWord').focus();}, 'title..');
 *
 */
function cmmnAlert(msg, callback, iconNum, btnText, title){
	if(iconNum == null || iconNum == ''){
		iconNum = 3;
	}
	Swal.fire({
		title : title != '' ? title : '' ,
		html: msg != '' ? msg : '' ,
		icon: cmmnIconCheck(iconNum),
		confirmButtonText: '확인',
		confirmButtonColor: '#0a7ce1',
	}).then(function () {
		if(callback != null && callback != "" && callback != 'undefined'){
			callback();
		}
	});

}

/**
 * 확인 / 취소 callback
 * 필수 파라미터 : message
 * 파라미터 : title, iconNum, btnText, callback
 * ex : cmmnConfirm('title!!','진행 하시겠습니까?' , 3, ["취소","확인"] ,cancle , ok);
 *
 */
function cmmnConfirm(msg, yCallback, nCallback, iconNum, yButton, nButton, title){
	if(iconNum == null || iconNum == ''){
		iconNum = 5;
	}
	if(yButton == null || yButton == ''){
		yButton = '확인';
	}
	if(nButton == null || nButton == ''){
		nButton = '취소';
	}

	Swal.fire({
		title : title != '' ? title : '' ,
		html: msg != '' ? msg : '' ,
		icon: cmmnIconCheck(iconNum),
		confirmButtonColor: '#0a7ce1',
		cancelButtonColor: '#fb9818',
		confirmButtonText: yButton,
		cancelButtonText: nButton,
		showCancelButton: true,
	}).then(function (then) {
		var result = then;
		if(result == null){result = false;}
		if(result.isConfirmed){
			if(yCallback != null && yCallback != "" && yCallback != 'undefined'){
				yCallback();
			}
		}else{
			if(nCallback != null && nCallback != "" && nCallback != 'undefined'){
				nCallback();
			}
		}
	});
}


/**
 * Textarea 입력 Byte체크
 * 
 * @param obj: tagId
 * @param maxByte: number
 *
 */
function fnCheckByte(obj, maxByte) {
	var str = $("#" + obj).val();
	var strLen = str.length;

	var rbyte = 0;
	var rlen = 0;
	var one_char = "";
	var str2 = "";

	for (var i = 0; i < strLen; i++) {
		one_char = str.charAt(i);
		if (escape(one_char).length > 4) {
			rbyte += 2; //한글2Byte
		} else {
			rbyte++; //영문 등 나머지 1Byte
		}

		if (rbyte <= maxByte) {
			rlen = i + 1; //return할 문자열 갯수
		}
	}

	if (rbyte > maxByte) {
		alert("최대 " + maxByte + "byte를 초과할 수 없습니다.")
		str2 = str.substr(0, rlen); //문자열 자르기
		$("#" + obj).val(str2);
		fnCheckByte(obj, maxByte);
	} else {
		$("#" + obj + "_byte").text("(" + rbyte + "/" + maxByte + "byte)");
	}
}

//자릿수 체크
function fnCheckStrSize(obj, maxStr) {
	var str = $("#" + obj).val();
	var strLen = str.length;

	var rbyte = 0;
	var rlen = 0;
	var one_char = "";
	var str2 = "";

	for (var i = 0; i < strLen; i++) {
		one_char = str.charAt(i);
		rbyte++;

		if (rbyte <= maxStr) {
			rlen = i + 1; //return할 문자열 갯수
		}
	}

	if (rbyte > maxStr) {
		alert("최대 " + maxStr + "글자를 초과할 수 없습니다.")
		str2 = str.substr(0, rlen); //문자열 자르기
		$("#" + obj).val(str2);
		fnCheckStrSize(obj, maxStr);
	} else {
		$("#" + obj + "_byte").text("(" + rbyte + "/" + maxStr + "자)");
	}
}

// 로딩바 append
function fnLodingAppend(obj){
	var aHtml = '';

	aHtml += '<div className="popup layer" id="lodingArea" style="text-align: center; margin: 30px 0px;" >';
	aHtml += '	<img src="/common/images/common/loadingImage.gif" style="display: inline-block;" >';
	aHtml += '</div>';

	$(obj).html(aHtml);
}

// 로딩바 시작
function fnLodingStart(){
	$('#lodingBar').addClass('on');
	$('#lodingBar').focus();

	dimMaker();
	$('.dim').css('z-index', '98')
}

// 로딩바 종료
function fnLodingStop() {
	$('#lodingBar').removeClass('on');
	dimRemove();
}

function fnReloadAlert(msg){
	window.onbeforeunload = function(e) {
		var dialogText = msg;
		e.returnValue = dialogText;
		return dialogText;
	};
}