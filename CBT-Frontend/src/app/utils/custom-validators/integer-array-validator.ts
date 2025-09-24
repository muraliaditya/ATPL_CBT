import { ValidatorFn, AbstractControl } from '@angular/forms';

export const IntegerArrayValidate: ValidatorFn = (
  control: AbstractControl
): { [key: string]: any } | null => {
  if (control.value) {
    let val: string = control.value.toString().trim();
    let valuesArray = val.split(',');
    if (val.startsWith('[') && val.endsWith(']') && val.includes(',')) {
      for (let i = 0; i < valuesArray.length; i++) {
        let result = parseInt(valuesArray[i]);
        console.log(result);
        if (isNaN(result)) {
          console.log('typeerror');

          return { typeError: true };
        }
      }
    } else if (val.startsWith('[') && val.endsWith(']') && val.length > 2) {
      let middleEle = val.replace(/[\[\]]/g, '');
      let parsedVal = parseInt(middleEle);
      if (isNaN(parsedVal)) {
        return { TypeError: true };
      } else {
        return null;
      }
    } else if (val.startsWith('[') && val.endsWith(']')) {
      console.log('no errror');

      return null;
    }
    console.log('typeerror');
    return { TypeError: true };
  }
  if (!control.value) {
    return { notFound: true };
  }
  console.log('no error');
  return null;
};
