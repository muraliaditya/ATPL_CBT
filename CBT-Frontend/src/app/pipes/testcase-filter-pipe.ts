import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'testcaseFilter',
})
export class TestcaseFilterPipe implements PipeTransform {
  transform<T>(
    values: Array<T>,
    testcaseFilter: (item: T) => boolean
  ): Array<T> {
    if (!values || !testcaseFilter) {
      return values;
    }
    return values.filter((item) => testcaseFilter(item));
  }
}
