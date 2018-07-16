/*
 * Copyright (c) 2018 Ramustha Studio All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 * Proprietary and confidential.
 */

CKEDITOR.dialog.add( 'myDialog', function() {
	return {
		title: 'My Dialog',
		minWidth: 400,
		minHeight: 200,
		contents: [
			{
				id: 'tab1',
				label: 'First Tab',
				title: 'First Tab',
				elements: [
					{
						id: 'input1',
						type: 'text',
						label: 'Text Field'
					},
					{
						id: 'select1',
						type: 'select',
						label: 'Select Field',
						items: [
							[ 'option1', 'value1' ],
							[ 'option2', 'value2' ]
						]
					}
				]
			},
			{
				id: 'tab2',
				label: 'Second Tab',
				title: 'Second Tab',
				elements: [
					{
						id: 'button1',
						type: 'button',
						label: 'Button Field'
					}
				]
			}
		]
	};
} );

