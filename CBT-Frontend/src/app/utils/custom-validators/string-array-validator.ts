import { ValidatorFn, AbstractControl } from '@angular/forms';

export const StringArrayValidate: ValidatorFn = (
  control: AbstractControl
): { [key: string]: any } | null => {
  if (control.value) {
    let val: string = control.value.toString().trim();
    let elements = val.slice(1, -1);
    let valuesArray = elements.split(',');
    if (!val.startsWith('[') || !val.endsWith(']')) {
      return { TypeError: true };
    }
    if (val.startsWith('[') && val.endsWith(']') && val.includes(',')) {
      console.log(valuesArray.length);
      for (let i = 0; i < valuesArray.length; i++) {
        console.log(valuesArray[i]);
        let result = valuesArray[i].trim();
        console.log(result);
        if (!result.length) {
          return { typeError: true };
        }
      }
      return null;
    }

    if (val.startsWith('[') && val.endsWith(']')) {
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
