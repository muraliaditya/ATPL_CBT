import { ActionReducerMap } from '@ngrx/store';
import { AppState } from './app.state';
import { contestReducer } from '../sub-stores/contest/contest.reducer';
export const appReducers: ActionReducerMap<AppState> = {
  contest: contestReducer,
};
